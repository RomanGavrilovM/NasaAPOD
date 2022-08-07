package com.example.nasaapod.api.epic

import retrofit2.http.GET

interface EpicApi {
    /**
     * EPIC - Earth Polychromatic Imaging Camera
     */

    @GET("api/natural")
    suspend fun getEpic(): List<EpicResponseDTO>
}