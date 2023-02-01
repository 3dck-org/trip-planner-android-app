package com.example.tripplanner.domain

import com.google.gson.annotations.SerializedName

data class Trips(
    @SerializedName("trip")
    val trip: TripsResponseItem
)