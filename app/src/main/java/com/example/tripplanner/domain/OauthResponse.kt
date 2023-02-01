package com.example.tripplanner.domain

data class OauthResponse(
    val access_token: String,
    val created_at: Int,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)