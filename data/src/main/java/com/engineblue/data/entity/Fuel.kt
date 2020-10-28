package com.engineblue.data.entity

import com.google.gson.annotations.SerializedName

data class Fuel(
    @SerializedName("IDProducto") val id: String? = null,
    @SerializedName("NombreProducto") val name: String? = null,
    @SerializedName("NombreProductoAbreviatura") val nameAbbreviature: String? = null
)