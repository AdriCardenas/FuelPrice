package com.engineblue.presentation.entity

import com.engineblue.domain.entity.FuelEntity

data class ListStationsState(
    val items: List<StationDisplayModel> = emptyList(),
    val selectedFuel: FuelEntity = FuelEntity(),
    val loading: Boolean = false
)