package com.example.nasaapod.api.mars

import retrofit2.http.GET
import retrofit2.http.Query

interface MarsApi {
    @GET("curiosity/latest_photos")
    suspend fun getMars(@Query("api_key")key: String):MarsResponse
}