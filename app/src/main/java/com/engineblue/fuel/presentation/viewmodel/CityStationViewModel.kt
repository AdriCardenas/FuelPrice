package com.engineblue.fuel.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.engineblue.fuel.domain.useCasesContract.GetCityStations
import com.engineblue.fuel.presentation.entity.CityStationUiState
import com.engineblue.fuel.presentation.intent.CityStationEvent
import com.engineblue.fuel.presentation.mapper.transformStationList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CityStationViewModel(
    private val getCityStations: GetCityStations,
) : MVIBaseViewModel<CityStationUiState, CityStationEvent>() {

    private fun loadStation(idCity: String, stationId: String, latitude: Double, longitude: Double) {
        execute {
            getCityStations(idCity).collect { stations ->
                updateUi {
                    if (!stations.isEmpty()) {
                        val nearStations = transformStationList(stations, Location("my_location"))
                        val station = nearStations.find { it.id == stationId }

                        if (station != null) {
                            CityStationUiState.Success(
                                nearStations = nearStations,
                                selectedStation = station
                            )
                        } else {
                            CityStationUiState.Error
                        }
                    } else {
                        CityStationUiState.Error
                    }
                }
            }
        }
    }

    override fun initState(): CityStationUiState = CityStationUiState.Idle

    override fun intentHandler() {
        executeIntent { event ->
            when (event) {
                is CityStationEvent.GetCityStations -> {
                    loadStation(event.idCity, event.stationId, event.latitude, event.longitude)
                }

                else -> updateUi { it }
            }
        }
    }

}