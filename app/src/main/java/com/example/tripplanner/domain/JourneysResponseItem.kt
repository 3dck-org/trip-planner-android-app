package com.example.tripplanner.domain

data class JourneysResponseItem(
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