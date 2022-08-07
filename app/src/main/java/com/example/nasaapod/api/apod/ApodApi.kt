package com.example.nasaapod.api.apod

import com.example.nasaapod.api.apod.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {
    /**
     * APOD -  Astronomy Picture of the Day
     */
    @GET("planetary/apod")
    suspend fun getAPOD(@Query("api_key") key: String,@Query("date") date : String  ): ApodResponse


}