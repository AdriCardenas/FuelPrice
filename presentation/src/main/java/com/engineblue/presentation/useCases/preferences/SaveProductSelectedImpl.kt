package com.engineblue.presentation.useCases.preferences

import com.engineblue.domain.repository.FuelRepository
import com.engineblue.domain.useCasesContract.preferences.SaveProductSelected

class SaveProductSelectedImpl(
    private val repository: FuelRepository,
) : SaveProductSelected {
    override fun saveProductSelected(id: String, name: String) {
        repository.saveFuelProduct(id, name)
    }
}