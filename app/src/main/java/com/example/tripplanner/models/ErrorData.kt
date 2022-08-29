package com.example.tripplanner.models

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("error")
    val error: Error
)