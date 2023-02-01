package com.example.tripplanner.domain

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("errorFull")
    val error: Error?,
    @SerializedName("error")
    val errorOfficial: List<String>?
)