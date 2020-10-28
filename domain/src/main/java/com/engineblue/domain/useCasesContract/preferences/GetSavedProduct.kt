package com.engineblue.domain.useCasesContract.preferences

import com.engineblue.domain.entity.FuelEntity

interface GetSavedProduct {
    fun getSavedProduct() :FuelEntity
}