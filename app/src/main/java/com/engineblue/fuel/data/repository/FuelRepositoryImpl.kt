package com.engineblue.fuel.data.repository

import com.engineblue.fuel.data.datasource.FuelApi
import com.engineblue.fuel.data.datasource.PreferenceSettings
import com.engineblue.fuel.data.entity.Fuel
import com.engineblue.fuel.data.mappers.transformFuelList
import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.repository.FuelRepository
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