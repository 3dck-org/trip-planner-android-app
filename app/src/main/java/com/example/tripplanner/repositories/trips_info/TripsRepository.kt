package com.example.tripplanner.repositories.trips_info

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.*
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripsRepository @Inject constructor(private val api: TripPlannerAPI) :
    ITripsRepository, BaseRepository() {

    override suspend fun getTrips(): Flow<Resource<TripsResponse>> =
        callOrError(api.getTripsAsync(mapOfHeaders))

    override suspend fun getTrips(city: String?, categories : String?): Flow<Resource<TripsResponse>> =
        callOrError(api.getTrips(mapOfHeaders, city, categories))

    override suspend fun getCurrentJourney(): Flow<Resource<CurrentJourneyResponse>> =
        callOrError(api.getCurrentJourneyAsync(mapOfHeaders))

    override suspend fun getFilters(): Flow<Resource<FiltersResponse>> =
        callOrError(api.getFiltersAsync(mapOfHeaders))
}
