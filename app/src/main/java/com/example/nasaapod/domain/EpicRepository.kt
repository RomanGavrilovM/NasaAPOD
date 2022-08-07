package com.example.nasaapod.domain

import com.example.nasaapod.api.epic.EpicResponseDTO

interface EpicRepository {
    suspend fun Epic(): List<EpicResponseDTO>
}