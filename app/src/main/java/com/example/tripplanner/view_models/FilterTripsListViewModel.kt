package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.db.dao.IDao
import com.example.tripplanner.db.entities.CategoryEntity
import com.example.tripplanner.db.entities.CityEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FilterTripsListViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var dao: IDao

    fun clearDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                clearAllCategories()
                clearAllCities()
            }
        }
    }

    fun getCategoryFilterFromDB() : Flow<List<CategoryEntity>> {
        return dao.getCategoryEntity()
    }

    fun getCityFilterFromDB() : Flow<List<CityEntity>> {
        return dao.getCitiesEntity()
    }

    fun updateCategoryFilterToDB(category: CategoryEntity) {
        dao.updateCategories(category)
    }

    fun updateCityFilterToDB(city: CityEntity) {
        dao.updateCity(city)
    }

    private fun clearAllCategories() {
        dao.clearAllCategories()
    }

    private fun clearAllCities() {
        dao.clearAllCities()
    }
}