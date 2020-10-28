package com.engineblue.data.mappers

import com.engineblue.data.entity.Fuel
import com.engineblue.domain.entity.FuelEntity
import java.util.stream.Collectors

fun transformFuelList(results: List<Fuel>?): List<FuelEntity> {
    return results?.map {
        FuelEntity(
            it.id,
            it.name,
            it.nameAbbreviature
        )
    }?.toList() ?: emptyList()
}