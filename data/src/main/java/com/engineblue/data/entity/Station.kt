package com.engineblue.data.entity

import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("IDEESS") val id: String? = null,

    @SerializedName("Latitud") val latitude: String? = null,
    @SerializedName("Longitud (WGS84)") val longitude: String? = null,

    @SerializedName("PrecioProducto") val prize: String? = null,

    @SerializedName("Rótulo") val name: String? = null,
    @SerializedName("Horario") val schedule: String? = null,

    @SerializedName("C.P.") val zipCode: String? = null,
    @SerializedName("Dirección") val direction: String? = null,
    @SerializedName("Municipio") val city: String? = null,
    @SerializedName("Provincia") val province: String? = null
)