package com.engineblue.fuel.domain.useCasesContract

import com.engineblue.fuel.domain.entity.FuelEntity
import com.engineblue.fuel.domain.entity.StationEntity

interface GetRemoteProducts {
    suspend fun getListRemoteFuels(): List<FuelEntity>
}