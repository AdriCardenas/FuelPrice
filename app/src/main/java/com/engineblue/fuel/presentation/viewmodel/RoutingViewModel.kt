package com.engineblue.fuel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedBoolean
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct

class RoutingViewModel(
    private val getSavedBoolean: GetSavedBoolean,
    private val getSavedProduct: GetSavedProduct
) : ViewModel() {
    fun getPreferencesFirstStart(key:String) : Boolean = !getSavedBoolean(key)
    fun getSelectedFuel() : FuelEntity = getSavedProduct()
}