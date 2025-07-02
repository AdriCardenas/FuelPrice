package com.engineblue.fuel.domain.entity

data class StationEntity(
    val id: String? = null,

    val latitude: String? = null,
    val longitude: String? = null,

    val priceSelected: String? = null,

    val gasPrice95: String? = null,
    val gasPrice98: String? = null,
    val dieselPrice: String? = null,
    val dieselPremiumPrice: String? = null,
    val dieselBPrice: String? = null,

    val name: String? = null,
    val schedule: String? = null,

    val zipCode: String? = null,
    val address: String? = null,
    val city: String? = null,
    val cityId: String? = null,
    val province: String? = null
)