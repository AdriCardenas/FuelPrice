package com.engineblue.fuel.domain.useCases

import com.engineblue.fuel.domain.repository.FuelRepository
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveProductSelected

class SaveProductSelectedImpl(
    private val repository: FuelRepository,
) : SaveProductSelected {
    override fun saveProductSelected(id: String, name: String) {
        repository.saveFuelProduct(id, name)
    }
}