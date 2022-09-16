package com.example.tripplanner.models

data class TripsResponseItem(
    val created_at: String,
    val description: String,
    val distance: String,
    val duration: Int,
    val image_url: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val user_id: String
)