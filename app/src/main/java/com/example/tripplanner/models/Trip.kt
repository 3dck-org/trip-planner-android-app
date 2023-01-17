package com.example.tripplanner.models

data class Trip(
    val created_at: String,
    val description: String,
    val distance: String,
    val duration: Int,
    val favorite: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val trip_place_infos: List<TripPlaceInfo>,
    val updated_at: String,
    val user_id: String
)