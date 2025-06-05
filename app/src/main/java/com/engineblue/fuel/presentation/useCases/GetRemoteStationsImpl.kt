package com.engineblue.fuel.presentation.useCases

import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.StationsRepository
import com.engineblue.fuel.domain.useCasesContract.GetRemoteStations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRemoteStationsImpl(
    private val repository: StationsRepository,
    private val dispatcher: CoroutineDispatcher
) : com.engineblue.fuel.domain.useCasesContract.GetRemoteStations {
    override suspend fun getListRemoteStations(idProduct: String): List<StationEntity> =
        withContext(dispatcher) {
            repository.getStations(idProduct)
        }
}