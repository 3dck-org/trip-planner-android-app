package com.example.tripplanner.repositories.trips_info

import com.example.tripplanner.domain.*
import kotlinx.coroutines.flow.Flow

interface ITripsRepository {
    suspend fun getTrips(): Flow<Resource<TripsResponse>>
    suspend fun getCurrentJourney() : Flow<Resource<CurrentJourneyResponse>>
    suspend fun getFilters() : Flow<Resource<FiltersResponse>>
}