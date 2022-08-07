package com.example.nasaapod.api.mars

import com.google.gson.annotations.SerializedName

data class MarsResponse (
    @SerializedName("latest_photos")
    val latestPhotos:List<LatestPhoto>,
        )

data class LatestPhoto (

    @SerializedName("id")
    val id:String,

    @SerializedName("sol")
    val sol:String,

    @SerializedName("img_src")
    val imgSrc:String,

    @SerializedName("earth_date")
    val earthDate:String,
    )
