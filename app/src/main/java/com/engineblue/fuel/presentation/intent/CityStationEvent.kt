package com.engineblue.fuel.presentation.intent

import com.engineblue.fuel.presentation.viewmodel.Event

sealed interface CityStationEvent : Event {

    data class GetCityStations(
        val idCity: String,
        val stationId: String,
        val latitude: Double,
        val longitude: Double
    ) : CityStationEvent

}