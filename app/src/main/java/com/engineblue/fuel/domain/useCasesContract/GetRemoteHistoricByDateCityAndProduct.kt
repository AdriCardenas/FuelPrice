package com.engineblue.fuel.domain.useCasesContract

import com.engineblue.fuel.domain.entity.StationEntity

fun interface GetRemoteHistoricByDateCityAndProduct {
    suspend operator fun invoke(date: String, idCity: String, idProduct: String): List<StationEntity>
}