package com.engineblue.fuel.data.entity

import com.squareup.moshi.Json


data class Fuel(
    @Json(name = "IDProducto") val id: String? = null,
    @Json(name = "NombreProducto") val name: String? = null,
    @Json(name ="NombreProductoAbreviatura") val nameAbbreviature: String? = null
)