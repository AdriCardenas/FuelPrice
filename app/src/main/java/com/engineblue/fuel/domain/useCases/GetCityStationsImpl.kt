package com.engineblue.fuel.domain.useCases

import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.StationsRepository
import com.engineblue.fuel.domain.useCasesContract.GetCityStations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetCityStationsImpl(
    private val repository: StationsRepository,
    private val dispatcher: CoroutineDispatcher
) : GetCityStations {

    override suspend fun invoke(idCity: String): Flow<List<StationEntity>> = withContext(dispatcher){
        flow {
            emit(repository.getStationsByCity(idCity))
        }
//        try {
//            emit(Result.Success(user))
//        } catch (e: Exception) {
//            emit(Result.Error(e.toAuthError()))
//        }

    }
}