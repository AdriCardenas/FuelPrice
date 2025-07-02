package com.engineblue.fuel.domain.useCasesContract

import com.engineblue.fuel.domain.entity.StationEntity

interface GetCityStations {
    suspend operator fun invoke(idCity:String): List<StationEntity>
}