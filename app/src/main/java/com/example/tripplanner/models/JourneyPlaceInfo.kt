package com.example.tripplanner.models

data class JourneyPlaceInfo(
    val created_at: String,
    val id: Int,
    val journey_id: Int,
    val place_id: Int,
    val status: String,
    val updated_at: String
)