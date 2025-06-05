package com.engineblue.fuel.presentation.entity

sealed class ListItemFuelProductDisplayModel {
    abstract fun areItemsTheSame(other: ListItemFuelProductDisplayModel): Boolean
    abstract fun areContentsTheSame(other: ListItemFuelProductDisplayModel): Boolean
}

data class FuelProductDisplayModel(
    val id: String,
    val name: String,
    val nameAbbreviature: String? = null
) : ListItemFuelProductDisplayModel() {

    override fun areItemsTheSame(other: ListItemFuelProductDisplayModel) =
        other is FuelProductDisplayModel && id == other.id

    override fun areContentsTheSame(other: ListItemFuelProductDisplayModel) =
        other == this
}