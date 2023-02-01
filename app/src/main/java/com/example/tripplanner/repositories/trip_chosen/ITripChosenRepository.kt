package com.example.tripplanner.repositories.trip_chosen

import com.example.tripplanner.domain.*
import kotlinx.coroutines.flow.Flow

interface ITripChosenRepository {
    suspend fun getTripsById(tripId: Int): Flow<Resource<TripByIdResponse>>
    suspend fun modifyFavoriteTrip(trip: Trips): Flow<Resource<TripsResponseItem>>
    suspend fun getCurrentJourney() : Flow<Resource<CurrentJourneyResponse>>
    suspend fun subscribeOnTrip(subscribeOnTripRequest: SubscribeOnTripRequest): Flow<Resource<SubscribeOnTripResponse>>
}