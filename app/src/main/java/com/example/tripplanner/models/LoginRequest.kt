package com.example.tripplanner.models

data class LoginRequest(
    val client_id: String,
    val client_secret: String,
    val email: String,
    val grant_type: String,
    val password: String,
    val refresh_token: String
)