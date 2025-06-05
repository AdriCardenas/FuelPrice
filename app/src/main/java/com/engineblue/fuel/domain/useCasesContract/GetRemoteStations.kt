package com.engineblue.fuel.domain.useCasesContract

import com.engineblue.fuel.domain.entity.StationEntity

interface GetRemoteStations {
   suspend fun getListRemoteStations(idProduct:String): List<StationEntity>
}