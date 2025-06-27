package com.engineblue.fuel.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationWrapperResponse(
    @SerialName("ListaEESSPrecio") val stations: List<Station>?
)