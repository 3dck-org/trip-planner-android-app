package com.example.tripplanner.models

data class SubscribeOnTripResponse(
    val completed: String,
    val created_at: String,
    val distance: String,
    val end_at: String,
    val id: Int,
    val start_at: String,
    val trip_id: Int,
    val updated_at: String,
    val user_id: Int
)