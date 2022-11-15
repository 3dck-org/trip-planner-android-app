package com.example.tripplanner.models

data class TripPlaceInfo(
    val comment: String,
    val order: Int,
    val place: Place,
    val place_id: Int,
    val trip_id: Int
)