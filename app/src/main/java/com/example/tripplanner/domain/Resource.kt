package com.example.tripplanner.domain

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Empty<T>(val status: String = "success") : Resource<T>()
    data class Error<T>(val errorData: ErrorData) : Resource<T>()
    data class Progress<T>(val data: T? = null) : Resource<T>()
}