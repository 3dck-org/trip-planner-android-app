package com.example.tripplanner.models

data class CurrentJourneyResponse(
    val completed: String,
    val created_at: String,
    val distance: String,
    val end_at: String,
    val id: Int,
    val start_at: String,
    val trip: Trip,
    val trip_id: Int,
    val updated_at: String,
    val user: UserDetails,
    val user_id: Int
)