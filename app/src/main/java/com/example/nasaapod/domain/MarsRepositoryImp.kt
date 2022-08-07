package com.example.nasaapod.domain

import com.example.nasaapod.api.mars.MarsApi
import com.example.nasaapod.api.mars.MarsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val api = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/")
    .client(
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    )
    .build()
    .create(MarsApi::class.java)


class MarsRepositoryImp : MarsRepository {
    override suspend fun Mars(): MarsResponse =
        api.getMars("GQr3kQbJTcVYc72YNrRg8GCNKbT6s618nfnS5COB")
}