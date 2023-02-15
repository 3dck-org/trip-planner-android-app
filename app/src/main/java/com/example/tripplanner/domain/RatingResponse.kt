package com.example.tripplanner.domain

data class RatingResponse(
    val created_at: String,
    val grade: Int,
    val id: Int,
    val trip_id: Int,
    val updated_at: String,
    val user_id: Int
)