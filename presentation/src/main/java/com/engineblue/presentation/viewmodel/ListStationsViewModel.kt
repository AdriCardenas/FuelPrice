package com.engineblue.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.useCasesContract.GetRemoteStations
import com.engineblue.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.presentation.entity.StationDisplayModel
import com.engineblue.presentation.mapper.transformStationList
import kotlinx.coroutines.launch

class ListStationsViewModel(
    private val getRemoteStations: GetRemoteStations,
    private val getSavedProduct: GetSavedProduct,
    scope: ViewModelScope = viewModelScope()
) :
    ViewModel(), ViewModelScope by scope {

    val selectedFuel = MutableLiveData<FuelEntity>()
    val loadingStatus = MutableLiveData<Boolean>()

    var latitude: Double? = null
    var longitude: Double? = null

    val stationList = MutableLiveData<List<StationDisplayModel>>()

    private fun getSavedProduct(): FuelEntity = getSavedProduct.getSavedProduct()

    fun loadStations() {
        launch {
            loadingStatus.postValue(true)
            stationList.postValue(listOf())

            val productSelected = getSavedProduct()

            productSelected.id?.let { productId ->
                selectedFuel.postValue(productSelected)

                getRemoteStations.getListRemoteStations(productId).let { result ->
                    stationList.postValue(if (latitude != null && longitude != null) {
                        val currentPosition = Location("Current Position")

                        currentPosition.latitude = latitude!!
                        currentPosition.longitude = longitude!!

                        val list = transformStationList(result, currentPosition)
                        val orderedList = list.sortedBy { it.distance }

                        setPricesColors(orderedList)
                    } else {
                        setPricesColors(transformStationList(result, null))
                    })
                }
            }

            loadingStatus.postValue(false)
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

}