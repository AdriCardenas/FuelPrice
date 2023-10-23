package com.engineblue.presentation.useCases

import com.engineblue.domain.entity.StationEntity
import com.engineblue.domain.repository.StationsRepository
import com.engineblue.domain.useCasesContract.GetRemoteHistoricByDateCityAndProduct
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