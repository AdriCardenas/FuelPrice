package com.engineblue.fuel.presentation.mapper

import android.location.Location
import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.presentation.entity.StationDisplayModel

fun transformStationList(
    results: List<StationEntity>?,
    currentPosition: Location?
): List<com.engineblue.fuel.presentation.entity.StationDisplayModel> {
    return results?.map {
        val location = Location(it.name)
        var distance = 0f

        if (it.latitude != null && it.longitude != null) {
            location.latitude = it.latitude?.replace(",", ".")?.toDouble()!!
            location.longitude = it.longitude?.replace(",", ".")?.toDouble()!!

            if (currentPosition != null) {
                distance = location.distanceTo(currentPosition)
            }
        }

        com.engineblue.fuel.presentation.entity.StationDisplayModel(
            it.id,
            location,
            distance,
            it.prize,
            it.name,
            it.schedule,
            it.zipCode,
            it.address,
            it.city,
            it.cityId,
            it.province
        )
    } ?: emptyList()
}