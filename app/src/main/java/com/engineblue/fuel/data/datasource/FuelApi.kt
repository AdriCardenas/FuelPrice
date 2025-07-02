package com.engineblue.fuel.data.datasource

import androidx.annotation.WorkerThread
import com.engineblue.fuel.data.entity.Fuel
import com.engineblue.fuel.data.entity.StationWrapperResponse

interface FuelApi {
    @WorkerThread
    suspend fun getFuels(): Result<List<Fuel>>


//    @GET("EstacionesTerrestres/FiltroCCAAProducto/01/{id}")

    @WorkerThread
    suspend fun getStationsByProduct(id: String): Result<StationWrapperResponse>

    @WorkerThread
    suspend fun getHistoricByDateCityAndProduct(
        fecha: String,
        idMunicipio: String,
        idProducto: String,
    ): Result<StationWrapperResponse>

    @WorkerThread
    suspend fun getCityStations(id: String): Result<StationWrapperResponse>
}