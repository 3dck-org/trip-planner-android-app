package com.example.tripplanner.repositories.current_journey

import com.example.tripplanner.domain.*
import kotlinx.coroutines.flow.Flow

interface ICurrentJourneyRepository {
    suspend fun getCurrentJourney() : Flow<Resource<CurrentJourneyResponse>>
    suspend fun modifyFavoriteTrip(trip: Trips): Flow<Resource<TripsResponseItem>>
    suspend fun modifyLike(tripId: Int, trip: TripLikeRequest): Flow<Resource<TripsResponseItem>>
    suspend fun unsubscribeOnTrip(subscribeOnTripRequest: UnsubscribeOnTripRequest, journeyId: Int):
            Flow<Resource<SubscribeOnTripResponse>>
}