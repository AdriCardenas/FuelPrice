package com.engineblue.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.useCasesContract.preferences.GetSavedBoolean
import com.engineblue.domain.useCasesContract.preferences.GetSavedProduct

class SplashViewModel(
    private val getSavedBoolean: GetSavedBoolean,
    private val getSavedProduct: GetSavedProduct
) : ViewModel() {
    fun getPreferencesFirstStart(key:String) : Boolean = !getSavedBoolean.getSavedBoolean(key)
    fun getSelectedFuel() : FuelEntity = getSavedProduct.getSavedProduct()
}