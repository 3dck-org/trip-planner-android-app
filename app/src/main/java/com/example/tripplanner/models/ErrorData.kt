package com.example.tripplanner.models

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("errorFull")
    val error: Error,
    @SerializedName("error")
    val errorOfficial: List<String>
)