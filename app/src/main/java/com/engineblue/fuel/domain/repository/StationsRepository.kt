package com.engineblue.fuel.domain.repository

import com.engineblue.fuel.domain.entity.StationEntity

interface StationsRepository {
    suspend fun getStations(idProduct: String): List<StationEntity>
    suspend fun getHistoricByDateCityAndProduct(
        date: String,
        idCity: String,
        idProduct: String
    ): List<StationEntity>
}