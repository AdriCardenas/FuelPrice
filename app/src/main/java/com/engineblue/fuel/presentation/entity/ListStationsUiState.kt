package com.engineblue.fuel.presentation.entity

import com.engineblue.fuel.domain.entity.FuelEntity

sealed class ListStationsUiState(open val selectedFuel: FuelEntity) {
    data class Idle(override val selectedFuel: FuelEntity = FuelEntity()) :
        ListStationsUiState(selectedFuel)

    data class Loading(override val selectedFuel: FuelEntity) : ListStationsUiState(selectedFuel)
    data class Success(
        val items: List<StationDisplayModel> = emptyList(),
        override val selectedFuel: FuelEntity
    ) : ListStationsUiState(selectedFuel)

    data class Error(override val selectedFuel: FuelEntity) : ListStationsUiState(selectedFuel)
}