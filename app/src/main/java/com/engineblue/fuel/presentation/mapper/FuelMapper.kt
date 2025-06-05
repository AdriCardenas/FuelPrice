package com.engineblue.fuel.presentation.mapper

import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.presentation.entity.FuelProductDisplayModel


fun transformFuelList(results: List<FuelEntity>?): List<FuelProductDisplayModel> {
    return results?.map {
        FuelProductDisplayModel(
            it.id ?: "",
            it.name ?: "",
            it.nameAbbreviature
        )
    } ?: emptyList()
}
