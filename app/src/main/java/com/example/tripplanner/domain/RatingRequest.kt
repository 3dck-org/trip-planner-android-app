package com.example.tripplanner.domain

data class RatingRequest(
    val grade: Int,
    val trip_id: Int
)