package com.engineblue.fuel.fuelprice.network

import androidx.core.graphics.get
import com.engineblue.fuel.data.datasource.FuelApi
import com.engineblue.fuel.data.entity.Fuel
import com.engineblue.fuel.data.entity.StationWrapperResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiDataSource(private val httpClient: HttpClient) : FuelApi {
    override suspend fun getFuels(): Result<List<Fuel>> {
        return try {
            val response: List<Fuel> = httpClient.get("Listados/ProductosPetroliferos")
                .body() // Deserialize the body to List<Headline>
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStationsByProduct(id: String): Result<StationWrapperResponse> {
        return try {
            val response: StationWrapperResponse =
                httpClient.get("EstacionesTerrestres/FiltroProducto/$id")
                    .body() // Deserialize the body to List<Headline>
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHistoricByDateCityAndProduct(
        fecha: String,
        idMunicipio: String,
        idProducto: String
    ): Result<StationWrapperResponse> {
        return try {
            val response: StationWrapperResponse =
                httpClient.get("EstacionesTerrestresHist/FiltroMunicipioProducto/$fecha/$idMunicipio/$idProducto")
                    .body() // Deserialize the body to List<Headline>
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}