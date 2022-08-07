package com.example.nasaapod.domain

import com.example.nasaapod.api.epic.EpicApi
import com.example.nasaapod.api.epic.EpicResponseDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val api = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://epic.gsfc.nasa.gov/")
    .client(OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()
    )
    .build()
    .create(EpicApi::class.java)


class EpicRepositoryImp:EpicRepository {
    override suspend fun Epic(): List<EpicResponseDTO> = api.getEpic()
}