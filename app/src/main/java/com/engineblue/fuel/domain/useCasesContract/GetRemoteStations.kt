package com.engineblue.fuel.domain.useCasesContract

import com.engineblue.fuel.domain.entity.StationEntity

interface GetRemoteStations {
   suspend operator fun invoke(idProduct:String): List<StationEntity>
}