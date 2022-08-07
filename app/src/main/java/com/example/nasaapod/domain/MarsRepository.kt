package com.example.nasaapod.domain

import com.example.nasaapod.api.mars.MarsResponse

interface MarsRepository {
    suspend fun Mars():MarsResponse
}