package com.example.tripplanner.domain.google_maps

import com.google.gson.annotations.SerializedName

data class EndLocation(
        @SerializedName("lat")
        var lat: Double?,
        @SerializedName("lng")
        var lng: Double?
)