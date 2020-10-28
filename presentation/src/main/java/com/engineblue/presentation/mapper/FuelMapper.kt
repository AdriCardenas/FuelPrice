package com.engineblue.presentation.mapper

import com.engineblue.domain.entity.FuelEntity
import com.engineblue.presentation.entity.FuelProductDisplayModel


fun transformFuelList(results: List<FuelEntity>?): List<FuelProductDisplayModel> {
    return results?.map {
        FuelProductDisplayModel(
            it.id,
            it.name,
            it.nameAbbreviature
        )
    } ?: emptyList()
}
