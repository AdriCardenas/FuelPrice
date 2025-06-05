package com.engineblue.fuel.data.datasource

import androidx.annotation.WorkerThread
import com.engineblue.fuel.data.entity.Fuel
import com.engineblue.fuel.data.entity.StationWrapperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FuelApi {
    @WorkerThread
    @GET("Listados/ProductosPetroliferos")
    suspend fun getFuels(): Response<List<Fuel>>


//    @GET("EstacionesTerrestres/FiltroCCAAProducto/01/{id}")

    @WorkerThread
    @GET("EstacionesTerrestres/FiltroProducto/{id}")
    suspend fun getStationsByProduct(@Path("id") id: String): Response<StationWrapperResponse>

    @WorkerThread
    @GET("EstacionesTerrestresHist/FiltroMunicipioProducto/{fecha}/{idMunicipio}/{idProducto}")
    suspend fun getHistoricByDateCityAndProduct(
        @Path("fecha") fecha: String,
        @Path("idMunicipio") idMunicipio: String,
        @Path("idProducto") idProducto: String,
    ): Response<StationWrapperResponse>
}