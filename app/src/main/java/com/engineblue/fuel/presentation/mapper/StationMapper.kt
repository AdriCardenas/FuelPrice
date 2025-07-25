package com.engineblue.fuel.presentation.mapper

import android.location.Location
import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.presentation.entity.StationDisplayModel

fun transformStationList(
    results: List<StationEntity>?,
    currentPosition: Location?
): List<StationDisplayModel> {
    return results?.map {
        val location = Location(it.name)
        var distance = 0f

        if (it.latitude != null && it.longitude != null) {
            location.latitude = it.latitude.replace(",", ".").toDouble()
            location.longitude = it.longitude.replace(",", ".").toDouble()

            if (currentPosition != null) {
                distance = location.distanceTo(currentPosition)
            }
        }

        StationDisplayModel(
            id = it.id,
            location = location,
            distance = distance,
            price = it.priceSelected,
            gasPrice95 = it.gasPrice95,
            gasPrice98 = it.gasPrice98,
            dieselPrice = it.dieselPrice,
            dieselPremiumPrice = it.dieselPremiumPrice,
            dieselBPrice = it.dieselBPrice,
            name = it.name,
            schedule = it.schedule,
            zipCode = it.zipCode,
            address = it.address,
            city = it.city,
            cityId = it.cityId,
            province = it.province
        )
    } ?: emptyList()
}