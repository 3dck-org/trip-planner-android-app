package com.example.tripplanner.repositories.trips_info

import com.example.tripplanner.models.*
import kotlinx.coroutines.flow.Flow

interface ITripsRepository {
    suspend fun getTrips(): Flow<Resource<TripsResponse>>
    suspend fun subscribeOnTrip(subscribeOnTripRequest: SubscribeOnTripRequest): Flow<Resource<SubscribeOnTripResponse>>
    suspend fun getJourneys(): Flow<Resource<JourneysResponse>>
}