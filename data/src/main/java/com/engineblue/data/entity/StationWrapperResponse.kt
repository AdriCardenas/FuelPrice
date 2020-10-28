package com.engineblue.data.entity

import com.google.gson.annotations.SerializedName

data class StationWrapperResponse(
    @SerializedName("ListaEESSPrecio") val stations: List<Station>?
)