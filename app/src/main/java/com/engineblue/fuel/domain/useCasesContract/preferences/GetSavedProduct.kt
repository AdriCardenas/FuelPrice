package com.engineblue.fuel.domain.useCasesContract.preferences

import com.engineblue.fuel.domain.entity.FuelEntity

interface GetSavedProduct {
    fun getSavedProduct() :FuelEntity
}