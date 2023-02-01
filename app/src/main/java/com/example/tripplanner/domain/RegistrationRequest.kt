package com.example.tripplanner.domain

data class RegistrationRequest(
    val client_id: String,
    val email: String,
    val login: String,
    val name: String,
    val password: String,
    val role_code: String,
    val surname: String
)