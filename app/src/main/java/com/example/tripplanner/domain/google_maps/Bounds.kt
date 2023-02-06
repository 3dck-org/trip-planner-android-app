package com.example.tripplanner.domain.google_maps
import com.google.gson.annotations.SerializedName

data class Bounds(
        @SerializedName("northeast")
        var northeast: Northeast?,
        @SerializedName("southwest")
        var southwest: Southwest?
)