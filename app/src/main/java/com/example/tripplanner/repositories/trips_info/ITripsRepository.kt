package com.example.tripplanner.repositories.trips_info

import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponse
import kotlinx.coroutines.flow.Flow

interface ITripsRepository {
    suspend fun getTrips(): Flow<Resource<TripsResponse>>
}