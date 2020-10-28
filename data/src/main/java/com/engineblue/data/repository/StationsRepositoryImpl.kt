package com.engineblue.data.repository

import com.engineblue.data.datasource.FuelApi
import com.engineblue.data.entity.StationWrapperResponse
import com.engineblue.data.mappers.transformStationList
import com.engineblue.domain.entity.StationEntity
import com.engineblue.domain.repository.StationsRepository
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

    override fun getRepositoryName(): String = "StationsRepository"
}