package com.engineblue.fuel.presentation.entity

import com.engineblue.fuel.presentation.viewmodel.State

sealed class CityStationUiState : State{
    object Idle : CityStationUiState()
    object Loading : CityStationUiState()
    data class Success(
        val nearStations: List<StationDisplayModel> = emptyList(),
        val selectedStation: StationDisplayModel= StationDisplayModel()
    ) : CityStationUiState()

    object Error : CityStationUiState()
}
