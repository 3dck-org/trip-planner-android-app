package com.example.tripplanner.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tripplanner.db.entities.CategoryEntity
import com.example.tripplanner.db.entities.CityEntity

@Dao
interface IDao {

    @Query("SELECT * FROM CityEntity")
    fun getCitiesEntity(): List<CityEntity>

    @Query("SELECT * FROM CategoryEntity")
    fun getCategoryEntity(): List<CategoryEntity>

    @Query("DELETE FROM CityEntity WHERE city_name = :city ")
    fun removeFromCitiesEntity(city: String)

    @Query("DELETE FROM CategoryEntity WHERE name = :name")
    fun removeFromCategoryEntity(name: String)

    @Query("DELETE FROM CityEntity")
    fun clearCitiesEntity()

    @Query("DELETE FROM CategoryEntity")
    fun clearCategoryEntity()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(city: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(category: CategoryEntity)
}