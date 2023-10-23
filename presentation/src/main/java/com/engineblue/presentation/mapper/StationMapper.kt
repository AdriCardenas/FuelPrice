package com.engineblue.presentation.mapper

import android.location.Location
import com.engineblue.domain.entity.StationEntity
import com.engineblue.presentation.entity.StationDisplayModel

fun transformStationList(
    results: List<StationEntity>?,
    currentPosition: Location?
): List<StationDisplayModel> {
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

        StationDisplayModel(
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