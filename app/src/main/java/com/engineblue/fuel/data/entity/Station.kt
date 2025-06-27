package com.engineblue.fuel.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station(
    @SerialName("IDEESS") val id: String? = null,

    @SerialName("Latitud") val latitude: String? = null,
    @SerialName("Longitud (WGS84)") val longitude: String? = null,

    @SerialName("PrecioProducto") val prize: String? = null,

    @SerialName("Rótulo") val name: String? = null,
    @SerialName("Horario") val schedule: String? = null,

    @SerialName("C.P.") val zipCode: String? = null,
    @SerialName("Dirección") val direction: String? = null,
    @SerialName("Municipio") val city: String? = null,
    @SerialName("IDMunicipio") val cityId: String? = null,
    @SerialName("Provincia") val province: String? = null
)