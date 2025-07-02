package com.engineblue.fuel.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station(
    @SerialName("IDEESS") val id: String? = null,

    @SerialName("Latitud") val latitude: String? = null,
    @SerialName("Longitud (WGS84)") val longitude: String? = null,

    @SerialName("PrecioProducto") val priceSelected: String? = null,

    @SerialName("Precio Gasolina 95 E5") val gasPrice95: String? = null,
    @SerialName("Precio Gasolina 98 E5") val gasPrice98: String? = null,
    @SerialName("Precio Gasoleo A") val dieselPrice: String? = null,
    @SerialName("Precio Gasoleo Premium") val dieselPremiumPrice: String? = null,
    @SerialName("Precio Gasoleo B") val dieselBPrice: String? = null,

    @SerialName("Rótulo") val name: String? = null,
    @SerialName("Horario") val schedule: String? = null,

    @SerialName("C.P.") val zipCode: String? = null,
    @SerialName("Dirección") val direction: String? = null,
    @SerialName("Municipio") val city: String? = null,
    @SerialName("IDMunicipio") val cityId: String? = null,
    @SerialName("Provincia") val province: String? = null
)