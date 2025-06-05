package com.engineblue.fuel.presentation.useCases

import com.engineblue.fuel.domain.repository.FuelRepository
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveProductSelected

class SaveProductSelectedImpl(
    private val repository: FuelRepository,
) : com.engineblue.fuel.domain.useCasesContract.preferences.SaveProductSelected {
    override fun saveProductSelected(id: String, name: String) {
        repository.saveFuelProduct(id, name)
    }
}