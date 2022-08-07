package com.example.nasaapod.domain

import com.example.nasaapod.api.apod.ApodResponse

interface NasaApodRepository {
    suspend fun Apod (date:String): ApodResponse

}