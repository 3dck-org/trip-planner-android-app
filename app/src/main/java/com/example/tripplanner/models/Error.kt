package com.example.tripplanner.models

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("message_loc")
    val message_loc: String,
    @SerializedName("request_id")
    val request_id: String,
    @SerializedName("type")
    val type: String
)