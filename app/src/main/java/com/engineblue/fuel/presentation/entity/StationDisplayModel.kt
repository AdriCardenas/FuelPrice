package com.engineblue.fuel.presentation.entity

import android.location.Location

sealed class StationItemFuelDisplayModel {
    abstract fun areItemsTheSame(other: com.engineblue.fuel.presentation.entity.StationItemFuelDisplayModel): Boolean
    abstract fun areContentsTheSame(other: com.engineblue.fuel.presentation.entity.StationItemFuelDisplayModel): Boolean
}

data class StationDisplayModel(
    val id: String? = null,

    val location: Location? = null,
    val distance: Float? = null,

    val price: String? = null,

    val name: String? = null,
    val schedule: String? = null,

    val zipCode: String? = null,
    val address: String? = null,
    val city: String? = null,
    val cityId: String? = null,
    val province: String? = null,
    var historic: List<com.engineblue.fuel.presentation.entity.HistoricStation> = listOf(),

    var priceStatus: com.engineblue.fuel.presentation.entity.StationDisplayModel.PriceStatus = com.engineblue.fuel.presentation.entity.StationDisplayModel.PriceStatus.UNASSIGNED
) : com.engineblue.fuel.presentation.entity.StationItemFuelDisplayModel() {

    enum class PriceStatus {
        CHEAP,
        REGULAR,
        EXPENSIVE,
        UNASSIGNED,
    }

    override fun areItemsTheSame(other: com.engineblue.fuel.presentation.entity.StationItemFuelDisplayModel) =
        other is com.engineblue.fuel.presentation.entity.StationDisplayModel && id == other.id

    override fun areContentsTheSame(other: com.engineblue.fuel.presentation.entity.StationItemFuelDisplayModel) =
        other is com.engineblue.fuel.presentation.entity.StationDisplayModel && other == this
                && other.price != this.price
                && other.priceStatus == this.priceStatus
                && other.address == this.address
}