package com.engineblue.data.datasource

import com.engineblue.data.entity.Fuel
import com.engineblue.data.entity.Station
import com.engineblue.data.entity.StationWrapperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FuelApi {
    @GET("Listados/ProductosPetroliferos")
    suspend fun getFuels(): Response<List<Fuel>>


//    @GET("EstacionesTerrestres/FiltroCCAAProducto/01/{id}")
    @GET("EstacionesTerrestres/FiltroProducto/{id}")
    suspend fun getStationsByProduct(@Path("id") id: String): Response<StationWrapperResponse>
}