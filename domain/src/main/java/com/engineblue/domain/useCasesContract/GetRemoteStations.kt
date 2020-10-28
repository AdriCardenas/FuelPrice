package com.engineblue.domain.useCasesContract

import com.engineblue.domain.entity.StationEntity

interface GetRemoteStations {
   suspend fun getListRemoteStations(idProduct:String): List<StationEntity>
}