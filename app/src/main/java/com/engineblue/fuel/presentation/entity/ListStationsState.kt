package com.engineblue.fuel.presentation.entity

import com.engineblue.fuel.domain.entity.FuelEntity

data class ListStationsState(
    val items: List<com.engineblue.fuel.presentation.entity.StationDisplayModel> = emptyList(),
    val selectedFuel: FuelEntity = FuelEntity(),
    val loading: Boolean = false
)