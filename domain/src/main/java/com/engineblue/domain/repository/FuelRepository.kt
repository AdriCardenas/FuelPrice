package com.engineblue.domain.repository

import com.engineblue.domain.entity.FuelEntity

interface FuelRepository {
    suspend fun getFuels(): List<FuelEntity>
    fun saveFuelProduct(id: String, name: String)
    fun getSavedFuel(): FuelEntity
}