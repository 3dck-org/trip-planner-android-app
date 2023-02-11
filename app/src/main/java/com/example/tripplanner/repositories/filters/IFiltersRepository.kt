package com.example.tripplanner.repositories.filters

import com.example.tripplanner.domain.FiltersResponse
import com.example.tripplanner.domain.Resource
import kotlinx.coroutines.flow.Flow

interface IFiltersRepository {
    suspend fun getFilters() : Flow<Resource<FiltersResponse>>
}