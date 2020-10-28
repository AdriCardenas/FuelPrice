package com.engineblue.presentation.useCases

import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.entity.StationEntity
import com.engineblue.domain.repository.FuelRepository
import com.engineblue.domain.useCasesContract.GetRemoteProducts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRemoteProductsImpl(
    private val repository: FuelRepository,
    private val dispatcher: CoroutineDispatcher
) : GetRemoteProducts {

    override suspend fun getListRemoteFuels(): List<FuelEntity> =
        withContext(dispatcher) {
            repository.getFuels()
        }
}