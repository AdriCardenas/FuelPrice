package com.engineblue.fuel.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fuel(
    @SerialName("IDProducto") val id: String? = null,
    @SerialName("NombreProducto") val name: String? = null,
    @SerialName("NombreProductoAbreviatura") val nameAbbreviature: String? = null
)