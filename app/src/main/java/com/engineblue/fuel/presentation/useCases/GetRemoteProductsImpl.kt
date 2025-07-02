package com.engineblue.fuel.presentation.useCases

import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.FuelRepository
import com.engineblue.fuel.domain.useCasesContract.GetRemoteProducts
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