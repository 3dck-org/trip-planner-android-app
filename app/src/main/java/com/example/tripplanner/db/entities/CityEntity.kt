package com.example.tripplanner.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityEntity")
data class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "city_is_picked")
    val city_is_picked: Boolean = false
)