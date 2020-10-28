package com.engineblue.presentation.useCases

import com.engineblue.domain.entity.StationEntity
import com.engineblue.domain.repository.StationsRepository
import com.engineblue.domain.useCasesContract.GetRemoteStations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRemoteStationsImpl(
    private val repository: StationsRepository,
    private val dispatcher: CoroutineDispatcher
) : GetRemoteStations {
    override suspend fun getListRemoteStations(idProduct: String): List<StationEntity> =
        withContext(dispatcher) {
            repository.getStations(idProduct)

        }
}