package com.engineblue.domain.useCasesContract

import com.engineblue.domain.entity.FuelEntity
import com.engineblue.domain.entity.StationEntity

interface GetRemoteProducts {
    suspend fun getListRemoteFuels(): List<FuelEntity>
}