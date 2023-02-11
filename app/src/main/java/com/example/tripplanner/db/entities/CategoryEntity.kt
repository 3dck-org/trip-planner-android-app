package com.example.tripplanner.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryEntity")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "name")
    val name: String
)