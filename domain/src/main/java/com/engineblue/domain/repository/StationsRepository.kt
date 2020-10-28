package com.engineblue.domain.repository

import com.engineblue.domain.entity.StationEntity

interface StationsRepository {
    suspend fun getStations(idProduct:String): List<StationEntity>
}