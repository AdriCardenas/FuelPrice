package com.engineblue.data.mappers

import com.engineblue.data.entity.Station
import com.engineblue.domain.entity.StationEntity
import java.util.logging.Level
import java.util.logging.Logger

fun transformStationList(list: List<Station>?, logger: Logger): List<StationEntity> {
    logger.log(Level.FINE, "List size" + list?.size)
    return list?.map { element ->
        StationEntity(
            element.id,
            element.latitude,
            element.longitude,
            element.prize,
            element.name,
            element.schedule,
            element.zipCode,
            element.direction,
            element.city,
            element.province
        )
    } ?: listOf()

}