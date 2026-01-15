package com.engineblue.fuel.domain.useCases

import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.StationsRepository
import com.engineblue.fuel.domain.useCasesContract.GetRemoteHistoricByDateCityAndProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRemoteHistoricByDateCityAndProductImpl(
    private val repository: StationsRepository,
    private val dispatcher: CoroutineDispatcher
) : GetRemoteHistoricByDateCityAndProduct {
    override suspend fun invoke(
        date: String,
        idCity: String,
        idProduct: String
    ): List<StationEntity> =
        withContext(dispatcher) {
            repository.getHistoricByDateCityAndProduct(date, idCity, idProduct)
        }
}