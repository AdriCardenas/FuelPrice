package com.engineblue.fuelprice.network

import com.engineblue.fuelprice.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createNetworkClient(baseUrl: String) =
    retrofitClient(baseUrl, httpClient())

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


private fun httpClient(): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()

    if(BuildConfig.DEBUG){
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    clientBuilder.readTimeout(30, TimeUnit.SECONDS)
    clientBuilder.writeTimeout(30, TimeUnit.SECONDS)
    return clientBuilder.build()
}