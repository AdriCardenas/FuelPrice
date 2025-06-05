package com.engineblue.fuel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.engineblue.fuel.domain.useCasesContract.GetRemoteProducts
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveProductSelected
import com.engineblue.fuel.presentation.entity.FuelProductDisplayModel
import com.engineblue.fuel.presentation.mapper.transformFuelList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FuelViewModel
    (
    private val getRemoteProducts: com.engineblue.fuel.domain.useCasesContract.GetRemoteProducts,
    private val saveProductSelected: com.engineblue.fuel.domain.useCasesContract.preferences.SaveProductSelected,
    scope: ViewModelScope = viewModelScope()
) :
    ViewModel(), ViewModelScope by scope {

    private val filteredListFuelForCars = listOf("1", "20", "3", "4", "5", "6", "17", "18")

    private val _fuelProductList = MutableStateFlow<List<FuelProductDisplayModel>>(emptyList())
    val fuelProductList: StateFlow<List<FuelProductDisplayModel>> = _fuelProductList

    init {
        loadProducts()
    }

    private fun loadProducts() {
        launch {
            getRemoteProducts.getListRemoteFuels().let { result ->
                _fuelProductList.value =
                    com.engineblue.fuel.presentation.mapper.transformFuelList(result).filter { filteredListFuelForCars.contains(it.id) }
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