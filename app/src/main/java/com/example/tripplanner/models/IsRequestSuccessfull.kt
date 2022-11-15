package com.example.tripplanner.models

import com.google.gson.annotations.SerializedName

data class IsRequestSuccessfull(
    @SerializedName("success")
    val isSuccessful: Boolean
)
