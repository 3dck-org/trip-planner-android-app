package com.example.tripplanner.models

import com.google.gson.annotations.SerializedName

data class TripsResponseItem(
    val created_at: String,
    val description: String,
    val distance: String,
    @SerializedName("favorite")
    val isFavourite: Boolean = false,
    val duration: Int,
    val image_url: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val user_id: String
)