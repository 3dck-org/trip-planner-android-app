package com.example.tripplanner.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityEntity")
data class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "city_name")
    val city_name: String
)