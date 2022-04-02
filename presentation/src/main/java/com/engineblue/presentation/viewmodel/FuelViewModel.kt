package com.engineblue.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engineblue.domain.useCasesContract.GetRemoteProducts
import com.engineblue.domain.useCasesContract.preferences.SaveProductSelected
import com.engineblue.presentation.entity.FuelProductDisplayModel
import kotlinx.coroutines.launch

class FuelViewModel
    (
    private val getRemoteProducts: GetRemoteProducts,
    private val saveProductSelected: SaveProductSelected,
    scope: ViewModelScope = viewModelScope()
) :
    ViewModel(), ViewModelScope by scope {

    private val filteredListFuelForCars = listOf("1", "20", "3", "4", "5", "6", "17", "18")

    val fuelProductList =
        MutableLiveData<List<FuelProductDisplayModel>>()

    fun loadProducts() {
        launch {
            getRemoteProducts.getListRemoteFuels().let { result ->
                fuelProductList.postValue(
                    result.map {
                        FuelProductDisplayModel(
                            it.id,
                            it.name,
                            it.nameAbbreviature
                        )
                    }.toList().filter { filteredListFuelForCars.contains(it.id) }
                )
            }
        }
    }

    fun saveProduct(id: String, name: String) {
        saveProductSelected.saveProductSelected(id, name)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}