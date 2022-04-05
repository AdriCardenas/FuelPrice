package com.engineblue.fuelprice.network

import com.engineblue.fuelprice.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun createNetworkClient(baseUrl: String) = retrofitClient(baseUrl, httpClient(), provideMoshi())

private fun provideMoshi() = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient, moshi: Moshi): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


private fun httpClient(): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    clientBuilder.readTimeout(30, TimeUnit.SECONDS)
    clientBuilder.writeTimeout(30, TimeUnit.SECONDS)
    return clientBuilder.build()
}