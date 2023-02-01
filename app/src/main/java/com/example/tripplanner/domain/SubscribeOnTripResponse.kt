package com.example.tripplanner.domain

data class SubscribeOnTripResponse(
    val id: Int,
    val trip_id: Int,
    val user_id: Int,
    val completed: String,
    val distance: String,
    val start_at: String,
    val end_at: String,
    val created_at: String,
    val updated_at: String,
    val journey_place_infos: List<JourneyPlaceInfo>
)