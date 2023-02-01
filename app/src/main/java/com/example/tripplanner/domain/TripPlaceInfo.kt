package com.example.tripplanner.domain

data class TripPlaceInfo(
    val comment: String,
    val order: Int,
    val place: Place,
    val place_id: Int,
    val trip_id: Int
)