package com.example.tripplanner.models

data class UserDetails(
    val birthday: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val image_url: String,
    val login: String,
    val name: String,
    val role_id: Int,
    val surname: String,
    val updated_at: String
)