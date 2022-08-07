package com.example.nasaapod.api.epic

import com.google.gson.annotations.SerializedName

data class EpicResponseDTO (
    @SerializedName("identifier")
    val identifier:String,

    @SerializedName("image")
    val image:String,

    @SerializedName("date")
    val date:String,

    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates = CentroidCoordinates(0.0,0.0)
)

data class CentroidCoordinates (
    @SerializedName("lat" ) var lat : Double,
    @SerializedName("lon" ) var lon : Double
)