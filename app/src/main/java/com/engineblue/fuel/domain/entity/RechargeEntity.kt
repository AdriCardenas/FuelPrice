package com.engineblue.fuel.domain.entity

data class RechargeEntity(
    val fuelEntity: Int,
    val carEntity: Int,
    val quantity: Float,
    val price: Float,
    val date: String,
    val kilometerCounter: Float
)