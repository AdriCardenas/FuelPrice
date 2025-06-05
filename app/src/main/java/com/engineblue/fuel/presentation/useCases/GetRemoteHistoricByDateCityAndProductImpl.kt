package com.engineblue.fuel.presentation.useCases

import com.engineblue.fuel.domain.entity.StationEntity
import com.engineblue.fuel.domain.repository.StationsRepository
import com.engineblue.fuel.domain.useCasesContract.GetRemoteHistoricByDateCityAndProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRemoteHistoricByDateCityAndProductImpl(
    private val repository: StationsRepository,
    private val dispatcher: CoroutineDispatcher
) : com.engineblue.fuel.domain.useCasesContract.GetRemoteHistoricByDateCityAndProduct {
    override suspend fun invoke(
        date: String,
        idCity: String,
        idProduct: String
    ): List<StationEntity> =
        withContext(dispatcher) {
            repository.getHistoricByDateCityAndProduct(date, idCity, idProduct)
        }
}