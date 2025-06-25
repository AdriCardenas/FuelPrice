package com.engineblue.fuel.presentation.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.useCasesContract.GetRemoteHistoricByDateCityAndProduct
import com.engineblue.fuel.domain.useCasesContract.GetRemoteStations
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.fuel.presentation.entity.HistoricStation
import com.engineblue.fuel.presentation.entity.ListStationsUiState
import com.engineblue.fuel.presentation.entity.StationDisplayModel
import com.engineblue.fuel.presentation.mapper.transformStationList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class ListStationsViewModel(
    private val getRemoteStations: GetRemoteStations,
    private val getRemoteHistoricByDateCityAndProduct: GetRemoteHistoricByDateCityAndProduct,
    private val getSavedProduct: GetSavedProduct,
    scope: ViewModelScope = viewModelScope()
) : ViewModel(), ViewModelScope by scope {


    // Backing property to avoid state updates from other classes
    private val _uiState: MutableStateFlow<ListStationsUiState> =
        MutableStateFlow(ListStationsUiState.Idle())

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<ListStationsUiState> = _uiState

    private var latitude: Double? = null
    private var longitude: Double? = null

    private fun getSelectedFuel(): FuelEntity = getSavedProduct()

    fun loadStations() {
        launch {
            val productSelected = getSelectedFuel()
            var items = emptyList<StationDisplayModel>()

            val currentPosition = Location("Current Position")
            val state = uiState.value
            if (productSelected.id != null && ((state is ListStationsUiState.Success && (productSelected.id != state.selectedFuel.id || currentPosition.latitude != latitude || currentPosition.longitude != longitude)) || state is ListStationsUiState.Error || state is ListStationsUiState.Idle)) {
                _uiState.value = ListStationsUiState.Loading(selectedFuel = productSelected)
                val remoteStations = getRemoteStations(productSelected.id)

                if (remoteStations.isNotEmpty()) {
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

                _uiState.value = ListStationsUiState.Success(
                    items = items, selectedFuel = productSelected
                )
            } else {
                if (_uiState.value !is ListStationsUiState.Success)
                    _uiState.value = ListStationsUiState.Error(selectedFuel = productSelected)
            }
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

    fun setLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
    }

    fun loadHistoric(item: StationDisplayModel) {
//        if (item.historic.isEmpty()) launch {
//            val calendar = Calendar.getInstance()
//
//            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//
//            val product = getSavedProduct()
//
//            val responses = arrayListOf<Deferred<Any>>()
//
//            val mapStationsAndHistory = hashMapOf<String, MutableList<HistoricStation>>()
//            if (item.cityId != null && item.id != null && product.id != null) {
//                for (i in 1..10) {
//                    calendar.add(Calendar.DATE, -1)
//                    val date = calendar.time
//                    responses.add(
//                        getHistoricFuelByDay(
//                            item, simpleDateFormat, date, product, mapStationsAndHistory
//                        )
//                    )
//                }
//
//
//                responses.awaitAll()
//
//                val currentState = uiState.value.items
//
//                val newList = currentState.map {
//                    if (it.id == item.id) {
//                        it.copy(historic = mapStationsAndHistory[item.id] ?: listOf())
//                    } else {
//                        it
//                    }
//                }
//
//
//                _uiState.value = _uiState.value.copy(
//                    items = newList,
//                    selectedFuel = uiState.value.selectedFuel,
//                    loading = uiState.value.loading
//                )
//            }
//        }
    }

    private fun getHistoricFuelByDay(
        item: StationDisplayModel,
        simpleDateFormat: SimpleDateFormat,
        date: Date,
        product: FuelEntity,
        mapStationsAndHistory: HashMap<String, MutableList<HistoricStation>>
    ) = async {
        val dateFormatted = simpleDateFormat.format(date)

        val response = getRemoteHistoricByDateCityAndProduct(
            dateFormatted, item.cityId!!, product.id!!
        )
        response.forEach {
            val prize = it.prize
            if (prize != null) {
                Log.d("LOG1**", "nuevo item $prize")
                val newItem = HistoricStation(dateFormatted, prize)
                if (mapStationsAndHistory.containsKey(it.id)) {
                    val currentList = mapStationsAndHistory[it.id]
                    currentList?.add(newItem)
                } else {
                    val newList = arrayListOf<HistoricStation>()
                    newList.add(newItem)
                    mapStationsAndHistory[it.id!!] = newList
                }
            }
        }
    }

    fun checkLoadStations() {
        val currentState = uiState.value
        if (currentState is ListStationsUiState.Success && currentState.items.isEmpty()) {
            loadStations()
        }
    }
}