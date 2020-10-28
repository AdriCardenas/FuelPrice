package com.engineblue.presentation.entity

import android.location.Location

sealed class StationItemFuelDisplayModel {
    abstract fun areItemsTheSame(other: StationItemFuelDisplayModel): Boolean
    abstract fun areContentsTheSame(other: StationItemFuelDisplayModel): Boolean
}

data class StationDisplayModel(
    val id: String? = null,

    val location: Location? = null,
    val distance: Float? = null,

    val prize: String? = null,

    val name: String? = null,
    val schedule: String? = null,

    val zipCode: String? = null,
    val address: String? = null,
    val city: String? = null,
    val province: String? = null
) : StationItemFuelDisplayModel() {

    override fun areItemsTheSame(other: StationItemFuelDisplayModel) =
        other is StationDisplayModel && id == other.id

    override fun areContentsTheSame(other: StationItemFuelDisplayModel) =
        other == this
}