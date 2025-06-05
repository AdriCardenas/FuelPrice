package com.engineblue.fuel.data.repository

import com.engineblue.fuel.data.datasource.FuelApi
import com.engineblue.fuel.data.entity.StationWrapperResponse
import com.engineblue.fuel.data.mappers.transformStationList
import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.StationsRepository
import java.util.logging.Logger

class StationsRepositoryImpl(
    private val api: FuelApi,
    private val logger: Logger,
) : StationsRepository, BaseRepository(logger) {
    override suspend fun getStations(idProduct: String): List<StationEntity> {
        val result: StationWrapperResponse = safeApiCall(
            call = { api.getStationsByProduct(idProduct) },
            errorMessage = "Error getting stations with id: $idProduct"
        )
            ?: return listOf()

        return transformStationList(result.stations, logger)
    }

    override suspend fun getHistoricByDateCityAndProduct(
        date: String,
        idCity: String,
        idProduct: String
    ): List<StationEntity> {
        val result: StationWrapperResponse = safeApiCall(
            call = { api.getHistoricByDateCityAndProduct(date, idCity, idProduct) },
            errorMessage = "Error getting stations with id: $idProduct"
        )
            ?: return listOf()

        return transformStationList(result.stations, logger)
    }

    override fun getRepositoryName(): String = "StationsRepository"
}