package com.example.tripplanner.domain

data class PasswordRequest(
    val new_password: String,
    val old_password: String
)