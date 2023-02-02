package com.example.tripplanner.domain

data class StatusResponse(
    val created_at: String,
    val id: Int,
    val journey_id: Int,
    val place_id: Int,
    val status: String,
    val updated_at: String
)