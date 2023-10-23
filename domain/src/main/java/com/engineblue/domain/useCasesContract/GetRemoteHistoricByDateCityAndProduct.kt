package com.engineblue.domain.useCasesContract

import com.engineblue.domain.entity.StationEntity

fun interface GetRemoteHistoricByDateCityAndProduct {
    suspend operator fun invoke(date: String, idCity: String, idProduct: String): List<StationEntity>
}