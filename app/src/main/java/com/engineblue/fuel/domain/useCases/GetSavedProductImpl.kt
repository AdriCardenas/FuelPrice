package com.engineblue.fuel.domain.useCases

import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.repository.FuelRepository
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct

class GetSavedProductImpl(
    private val repository: FuelRepository,
) : GetSavedProduct {
    override fun invoke(): FuelEntity = repository.getSavedFuel()
}