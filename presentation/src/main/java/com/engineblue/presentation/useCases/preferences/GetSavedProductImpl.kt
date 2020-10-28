package com.engineblue.presentation.useCases.preferences

import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.repository.FuelRepository
import com.engineblue.domain.useCasesContract.preferences.GetSavedProduct

class GetSavedProductImpl(
    private val repository: FuelRepository,
) : GetSavedProduct {
    override fun getSavedProduct(): FuelEntity =
        repository.getSavedFuel()
}