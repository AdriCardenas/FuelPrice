package com.engineblue.fuel.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.engineblue.fuel.domain.useCasesContract.GetCityStations
import com.engineblue.fuel.presentation.entity.CityStationUiState
import com.engineblue.fuel.presentation.mapper.transformStationList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CityStationViewModel(
    private val getCityStations: GetCityStations,
) : ViewModel() {

    private val _stationCity: MutableStateFlow<CityStationUiState> =
        MutableStateFlow(CityStationUiState.Idle)
    val stationCity: StateFlow<CityStationUiState> = _stationCity

    fun loadStation(idCity: String, stationId: String, latitude: Double, longitude: Double) {
        viewModelScope().launch {
            if (stationCity.value != CityStationUiState.Loading) {
                _stationCity.value = CityStationUiState.Loading

                val currentPosition = Location("my_location")

                val stations = getCityStations(idCity)

                if (!stations.isEmpty()) {
                    val nearStations = transformStationList(stations, currentPosition)
                    val station = nearStations.find { it.id == stationId }

                    if (station != null) {
                        _stationCity.value = CityStationUiState.Success(
                            nearStations.filter { it.id != stationId },
                            station
                        )
                    } else {
                        _stationCity.value = CityStationUiState.Error
                    }
                } else {
                    _stationCity.value = CityStationUiState.Error
                }
            }
        }
    }

}