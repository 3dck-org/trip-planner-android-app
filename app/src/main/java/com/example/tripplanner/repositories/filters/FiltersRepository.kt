package com.example.tripplanner.repositories.filters

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.FiltersResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FiltersRepository @Inject constructor(private val api: TripPlannerAPI) :
    IFiltersRepository, BaseRepository() {

    override suspend fun getFilters(): Flow<Resource<FiltersResponse>> =
        callOrError(api.getFiltersAsync(mapOfHeaders))
}