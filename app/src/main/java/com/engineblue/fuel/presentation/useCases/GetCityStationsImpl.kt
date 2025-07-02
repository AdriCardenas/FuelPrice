package com.engineblue.fuel.presentation.useCases

import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.StationsRepository
import com.engineblue.fuel.domain.useCasesContract.GetCityStations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCityStationsImpl(
    private val repository: StationsRepository,
    private val dispatcher: CoroutineDispatcher
) : GetCityStations {

    override suspend fun invoke(idCity: String): List<StationEntity> = withContext(dispatcher){
        repository.getStationsByCity(idCity)
    }
}