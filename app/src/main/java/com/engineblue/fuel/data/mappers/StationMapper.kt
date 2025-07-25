package com.engineblue.fuel.data.mappers

import com.engineblue.fuel.data.entity.Station
import com.engineblue.fuel.domain.entity.StationEntity
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.collections.filter

fun transformStationList(list: List<Station>?, logger: Logger): List<StationEntity> {
    logger.log(Level.FINE, "List size" + list?.size)
    return list?.filter { it.gasPrice95 != null || it.gasPrice98 != null || it.dieselPrice != null || it.dieselPremiumPrice != null || it.dieselBPrice != null || it.priceSelected != null }
        ?.map { element ->
            StationEntity(
                element.id,
                element.latitude,
                element.longitude,
                element.priceSelected,
                element.gasPrice95,
                element.gasPrice98,
                element.dieselPrice,
                element.dieselPremiumPrice,
                element.dieselBPrice,
                element.name,
                element.schedule,
                element.zipCode,
                element.direction,
                element.city,
                element.cityId,
                element.province
            )
        } ?: listOf()

}