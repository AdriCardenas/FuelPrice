package com.engineblue.fuel.presentation.useCases

import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.repository.FuelRepository
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct

class GetSavedProductImpl(
    private val repository: FuelRepository,
) : com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct {
    override fun getSavedProduct(): FuelEntity =
        repository.getSavedFuel()
}