package com.engineblue.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.useCasesContract.GetRemoteStations
import com.engineblue.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.presentation.entity.ListStationsState
import com.engineblue.presentation.entity.StationDisplayModel
import com.engineblue.presentation.mapper.transformStationList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListStationsViewModel(
    private val getRemoteStations: GetRemoteStations,
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
            _uiState.value = uiState.value.copy(
                loading = true,
                items = emptyList()
            )

            val productSelected = getSavedProduct()
            var items = emptyList<StationDisplayModel>()

            if (productSelected.id != null) {
                val remoteStations = getRemoteStations.getListRemoteStations(productSelected.id!!)

                items = if (latitude != null && longitude != null) {
                    val currentPosition = Location("Current Position")

                    currentPosition.latitude = latitude!!
                    currentPosition.longitude = longitude!!

                    val list = transformStationList(remoteStations, currentPosition)
                    val orderedList = list.sortedBy { it.distance }

                    setPricesColors(orderedList)
                } else {
                    setPricesColors(transformStationList(remoteStations, null))
                }


            }

            _uiState.value = uiState.value.copy(
                loading = false,
                items = items,
                selectedFuel = productSelected
            )
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

}