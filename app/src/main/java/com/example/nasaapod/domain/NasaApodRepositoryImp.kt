package com.example.nasaapod.domain

import com.example.nasaapod.api.apod.ApodResponse
import com.example.nasaapod.api.apod.ApodApi
import com.google.android.material.snackbar.Snackbar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val api = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://api.nasa.gov/")
    .client(OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }

        .build()
    )
    .build()
    .create(ApodApi::class.java)


/**
ключ по хорошему не нужно передавать но для удобства учебных целей оставил так
 */

class NasaApodRepositoryImp : NasaApodRepository {
    override suspend fun Apod(date: String): ApodResponse =
        api.getAPOD("GQr3kQbJTcVYc72YNrRg8GCNKbT6s618nfnS5COB", date)
}