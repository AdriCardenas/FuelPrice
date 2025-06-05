package com.engineblue.fuel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedBoolean
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveBoolean

class RoutingViewModel(
    private val getSavedBoolean: com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedBoolean,
    private val getSavedProduct: com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct
) : ViewModel() {
    fun getPreferencesFirstStart(key:String) : Boolean = !getSavedBoolean.getSavedBoolean(key)
    fun getSelectedFuel() : FuelEntity = getSavedProduct.getSavedProduct()
}