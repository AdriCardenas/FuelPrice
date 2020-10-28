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
                    }.toList()
                )
            }
        }
    }

    fun saveProduct(id:String, name:String){
        saveProductSelected.saveProductSelected(id, name)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}