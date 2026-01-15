package com.engineblue.fuel.domain.useCases

import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.StationsRepository
import com.engineblue.fuel.domain.useCasesContract.GetRemoteStations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRemoteStationsImpl(
    private val repository: StationsRepository,
    private val dispatcher: CoroutineDispatcher
) : GetRemoteStations {

    override suspend fun invoke(idProduct: String): List<StationEntity> = withContext(dispatcher) {
        repository.getStations(idProduct)
    }
}