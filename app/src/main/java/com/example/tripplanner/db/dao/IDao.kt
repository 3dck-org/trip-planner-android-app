package com.example.tripplanner.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tripplanner.db.entities.CategoryEntity
import com.example.tripplanner.db.entities.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IDao {

    //getters
    @Query("SELECT * FROM CityEntity")
    fun getCitiesEntity(): Flow<List<CityEntity>>

    @Query("SELECT * FROM CategoryEntity")
    fun getCategoryEntity(): Flow<List<CategoryEntity>>

    //updates
    @Update
    fun updateCategories(category: CategoryEntity)

    @Update
    fun updateCity(city: CityEntity)

    //clean
    @Query("UPDATE CategoryEntity SET isPicked=0")
    fun clearAllCategories()

    @Query("UPDATE CityEntity SET city_is_picked=0")
    fun clearAllCities()

    //insertions
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCategories(categoryList: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCities(categoryList: List<CityEntity>)
}