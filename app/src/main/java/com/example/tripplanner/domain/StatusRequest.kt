package com.example.tripplanner.domain

data class StatusRequest(
    val journey_id: Int,
    val place_id: Int,
    val status: String
)