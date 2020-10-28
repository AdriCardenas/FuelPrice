package com.engineblue.data.repository

import com.engineblue.data.datasource.FuelApi
import com.engineblue.data.datasource.PreferenceSettings
import com.engineblue.data.entity.Fuel
import com.engineblue.data.entity.Station
import com.engineblue.data.entity.StationWrapperResponse
import com.engineblue.data.mappers.transformFuelList
import com.engineblue.data.mappers.transformStationList
import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.entity.StationEntity
import com.engineblue.domain.repository.FuelRepository
import java.util.logging.Logger

class FuelRepositoryImpl(
    private val api: FuelApi,
    logger: Logger,
    private val settingsDataSource: PreferenceSettings,
) : BaseRepository(logger), FuelRepository {

    companion object {
        const val NAME = "FuelRepository";
    }

    override suspend fun getFuels(): List<FuelEntity> {
        val result: List<Fuel>? = safeApiCall(
            call = { api.getFuels() },
            errorMessage = "Error getting fuels"
        )

        return transformFuelList(result)
    }

    override fun saveFuelProduct(id: String, name: String) {
        settingsDataSource.saveProduct(id, name)
    }

    override fun getSavedFuel(): FuelEntity =
        settingsDataSource.getSavedProduct()


    override fun getRepositoryName(): String {
        return NAME
    }
}