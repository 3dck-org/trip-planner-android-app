package com.example.tripplanner.domain.google_maps
import com.google.gson.annotations.SerializedName

data class Polyline(
        @SerializedName("points")
        var points: String?
)