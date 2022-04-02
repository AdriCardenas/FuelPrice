package com.engineblue.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.useCasesContract.GetRemoteStations
import com.engineblue.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.domain.useCasesContract.preferences.SaveProductSelected
import com.engineblue.presentation.entity.StationDisplayModel
import com.engineblue.presentation.mapper.transformStationList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListStationsViewModel(
    private val getRemoteStations: GetRemoteStations,
    private val getSavedProduct: GetSavedProduct,
    scope: ViewModelScope = viewModelScope()
) :
    ViewModel(), ViewModelScope by scope {

    val selectedFuel = MutableLiveData<FuelEntity>()

    var latitude: Double? = null
    var longitude: Double? = null

    val stationList =
        MutableLiveData<List<StationDisplayModel>>()

    private fun getSavedProduct(): FuelEntity = getSavedProduct.getSavedProduct()

    fun loadStations() {
        launch {
            val productSelected = getSavedProduct()

            productSelected.id?.let { productId ->
                selectedFuel.postValue(productSelected)

                getRemoteStations.getListRemoteStations(productId).let { result ->
                    if (latitude != null && longitude != null) {
                        val currentPosition = Location("Current Position")

                        currentPosition.latitude = latitude!!
                        currentPosition.longitude = longitude!!

                        val list = transformStationList(result, currentPosition)
                        val orderedList = list.sortedBy { it.distance }

                        stationList.postValue(orderedList)
                    } else {
                        stationList.postValue(transformStationList(result, null))
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}