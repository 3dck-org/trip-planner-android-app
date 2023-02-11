package com.example.tripplanner.domain

import com.example.tripplanner.db.entities.CategoryEntity

data class FiltersResponse(
    val categories: List<CategoryEntity>,
    val cities: List<String>
)