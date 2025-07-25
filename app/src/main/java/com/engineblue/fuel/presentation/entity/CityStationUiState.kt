package com.engineblue.fuel.presentation.entity

sealed class CityStationUiState {
    object Idle : CityStationUiState()
    object Loading : CityStationUiState()
    data class Success(
        val nearStations: List<StationDisplayModel> = emptyList(),
        val selectedStation: StationDisplayModel= StationDisplayModel()
    ) : CityStationUiState()

    object Error : CityStationUiState()
}
