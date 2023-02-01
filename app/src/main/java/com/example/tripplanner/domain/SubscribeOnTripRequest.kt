package com.example.tripplanner.domain

data class SubscribeOnTripRequest(
    val start_at: String,
    val trip_id: Int
)

data class UnsubscribeOnTripRequest(
    val end_at: String,
    val completed: Boolean = true
)