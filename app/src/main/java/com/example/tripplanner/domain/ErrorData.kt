package com.example.tripplanner.domain

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("error_code")
    val error_code: Int,
    @SerializedName("error_message")
    val error_message: List<String>
)