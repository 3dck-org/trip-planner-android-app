package com.example.tripplanner.domain

data class FiltersResponse(
    val categories: List<Category>,
    val cities: List<String>
)