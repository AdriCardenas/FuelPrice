package com.engineblue.presentation.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.useCasesContract.GetRemoteHistoricByDateCityAndProduct
import com.engineblue.domain.useCasesContract.GetRemoteStations
import com.engineblue.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.presentation.entity.ListStationsState
import com.engineblue.presentation.entity.StationDisplayModel
import com.engineblue.presentation.mapper.transformStationList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ListStationsViewModel(
    private val getRemoteStations: GetRemoteStations,
    private val getRemoteHistoricByDateCityAndProduct: GetRemoteHistoricByDateCityAndProduct,
    private val getSavedProduct: GetSavedProduct,
    scope: ViewModelScope = viewModelScope()
) :
    ViewModel(), ViewModelScope by scope {


    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(ListStationsState())

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<ListStationsState> = _uiState

    private var latitude: Double? = null
    private var longitude: Double? = null

    private fun getSavedProduct(): FuelEntity = getSavedProduct.getSavedProduct()

    fun loadStations() {
        launch {
            val model = uiState.value.copy(
                loading = true,
                items = emptyList()
            )
            _uiState.value = model
            val productSelected = getSavedProduct()
            var items = emptyList<StationDisplayModel>()

            val currentPosition = Location("Current Position")

            if (productSelected.id != null
                && (productSelected.id != _uiState.value.selectedFuel.id || currentPosition.latitude != latitude || currentPosition.longitude != longitude)
            ) {
                val remoteStations = getRemoteStations.getListRemoteStations(productSelected.id!!)

                items = if (latitude != null && longitude != null) {

                    currentPosition.latitude = latitude!!
                    currentPosition.longitude = longitude!!

                    val list = transformStationList(remoteStations, currentPosition)
                    val orderedList = list.sortedBy { it.distance }

                    setPricesColors(orderedList)
                } else {
                    setPricesColors(transformStationList(remoteStations, null))
                }
            }

            val model2 = uiState.value.copy(
                loading = false,
                items = items,
                selectedFuel = productSelected
            )
            _uiState.value = model2
        }
    }

    private fun setPricesColors(list: List<StationDisplayModel>): List<StationDisplayModel> {
        val prices = list.mapNotNull { it.price?.replace(",", ".")?.toFloat() }

        val mean = prices.average()

        list.forEach { station ->
            val price = station.price?.replace(",", ".")?.toFloatOrNull()
            if (price != null) {
                if (price < (mean - 0.05)) {
                    station.priceStatus = StationDisplayModel.PriceStatus.CHEAP
                } else if ((mean - 0.05) < price && price < (mean + 0.05)) {
                    station.priceStatus = StationDisplayModel.PriceStatus.REGULAR
                } else {
                    station.priceStatus = StationDisplayModel.PriceStatus.EXPENSIVE
                }
            }
        }

        return list
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun setLocation(latitude: Double?, longitude: Double?) {
        this.latitude = latitude
        this.longitude = longitude
    }

    fun loadHistoric(item: StationDisplayModel) {
        launch {
            val calendar = Calendar.getInstance()

            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] - 1

            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            val product = getSavedProduct()

            if (item.cityId != null && product.id != null) {
                val list = getRemoteHistoricByDateCityAndProduct(
                    simpleDateFormat.format(calendar.time),
                    item.cityId,
                    product.id!!
                )

                list.forEach {
                    Log.d("Encontrado", it.toString())
                }
            }
        }
    }

}