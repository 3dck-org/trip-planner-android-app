package com.example.tripplanner.domain

data class Trip(
    val created_at: String,
    val description: String,
    val distance: String,
    val duration: Int,
    val favorite: String,
    val average_rating: String?,
    val id: Int,
    val image_url: String,
    val name: String,
    val updated_at: String,
    val user_id: String,
    val user: UserDetails,
    val trip_place_infos: List<TripPlaceInfo>
)