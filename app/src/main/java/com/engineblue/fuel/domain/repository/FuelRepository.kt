package com.engineblue.fuel.domain.repository

import com.engineblue.fuel.domain.entity.FuelEntity

interface FuelRepository {
    suspend fun getFuels(): List<FuelEntity>
    fun saveFuelProduct(id: String, name: String)
    fun getSavedFuel(): FuelEntity
}