package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.db.dao.IDao
import com.example.tripplanner.db.entities.CategoryEntity
import com.example.tripplanner.domain.FiltersResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.filters.FiltersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FilterTripsListViewModel @Inject constructor(private val filtersRepository: FiltersRepository) :
    ViewModel() {

    @Inject
    lateinit var dao: IDao

    private val _response =
        MutableStateFlow<Resource<FiltersResponse>>(Resource.Progress())
    val response: StateFlow<Resource<FiltersResponse>>
        get() = _response

    fun getFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersRepository.getFilters()
                .collect { _response.emit(it) }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                dao.clearCitiesEntity()
                dao.clearCategoryEntity()
            }
        }
    }

    fun getCategoryFilterFromDB() : List<CategoryEntity> {
        return dao.getCategoryEntity()
    }

    fun addCategoryFilterToDB(category: CategoryEntity) {
        dao.insertCategories(category)
    }

    fun removeCategoryFilterToDB(categoryName: String) {
        dao.removeFromCategoryEntity(categoryName)
    }
}