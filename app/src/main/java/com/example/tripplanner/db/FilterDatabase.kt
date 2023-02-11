package com.example.tripplanner.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tripplanner.db.dao.IDao
import com.example.tripplanner.db.entities.CategoryEntity
import com.example.tripplanner.db.entities.CityEntity

@Database(entities = [CategoryEntity::class, CityEntity::class], version = 6)
abstract class FilterDatabase : RoomDatabase(){

    abstract fun dao(): IDao

    companion object {
        const val DB_NAME = "filter.db"
    }
}