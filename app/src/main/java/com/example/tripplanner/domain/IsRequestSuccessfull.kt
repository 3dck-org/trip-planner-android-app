package com.example.tripplanner.domain

import com.google.gson.annotations.SerializedName

data class IsRequestSuccessfull(
    @SerializedName("success")
    val isSuccessful: Boolean
)
