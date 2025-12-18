package com.engineblue.fuel.domain.useCasesContract

import com.engineblue.fuel.domain.entity.StationEntity
import kotlinx.coroutines.flow.Flow

interface GetCityStations {
    suspend operator fun invoke(idCity:String): Flow<List<StationEntity>>
}