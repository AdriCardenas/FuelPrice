package com.engineblue.data.entity

import com.squareup.moshi.Json

data class StationWrapperResponse(
    @Json(name ="ListaEESSPrecio") val stations: List<Station>?
)