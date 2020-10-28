package com.engineblue.presentation.entity

sealed class ListItemFuelProductDisplayModel {
    abstract fun areItemsTheSame(other: ListItemFuelProductDisplayModel): Boolean
    abstract fun areContentsTheSame(other: ListItemFuelProductDisplayModel): Boolean
}

data class FuelProductDisplayModel(
    val id: String? = null,
    val name: String? = null,
    val nameAbbreviature: String? = null
) : ListItemFuelProductDisplayModel() {

    override fun areItemsTheSame(other: ListItemFuelProductDisplayModel) =
        other is FuelProductDisplayModel && id == other.id

    override fun areContentsTheSame(other: ListItemFuelProductDisplayModel) =
        other == this
}