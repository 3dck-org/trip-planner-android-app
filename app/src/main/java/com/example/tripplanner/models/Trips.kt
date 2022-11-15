package com.example.tripplanner.models

import com.google.gson.annotations.SerializedName

data class Trips(
    @SerializedName("trip")
    val trip: TripsResponseItem
)