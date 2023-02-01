package com.example.tripplanner.domain

data class Place(
    val address: Address,
    val address_id: Int,
    val category_dictionaries: List<CategoryDictionary>,
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val point: Point,
    val updated_at: String
)