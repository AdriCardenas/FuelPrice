package com.engineblue.data.entity

import com.squareup.moshi.Json

data class Station(
    @Json(name ="IDEESS") val id: String? = null,

    @Json(name ="Latitud") val latitude: String? = null,
    @Json(name ="Longitud (WGS84)") val longitude: String? = null,

    @Json(name ="PrecioProducto") val prize: String? = null,

    @Json(name ="Rótulo") val name: String? = null,
    @Json(name ="Horario") val schedule: String? = null,

    @Json(name ="C.P.") val zipCode: String? = null,
    @Json(name ="Dirección") val direction: String? = null,
    @Json(name ="Municipio") val city: String? = null,
    @Json(name ="Provincia") val province: String? = null
)