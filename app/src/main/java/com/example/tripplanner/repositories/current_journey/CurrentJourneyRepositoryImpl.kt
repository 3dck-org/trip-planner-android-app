package com.example.tripplanner.repositories.current_journey

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.*
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentJourneyRepositoryImpl @Inject constructor(private val api: TripPlannerAPI) :
    ICurrentJourneyRepository, BaseRepository() {

    override suspend fun getCurrentJourney(): Flow<Resource<CurrentJourneyResponse>> =
        callOrError(api.getCurrentJourneyAsync(mapOfHeaders))

    override suspend fun updateStatus(statusRequest: StatusRequest): Flow<Resource<StatusResponse>> =
        callOrError(api.updateStatusAsync(mapOfHeaders, statusRequest))

    override suspend fun modifyFavoriteTrip(
        trip: Trips
    ): Flow<Resource<TripsResponseItem>> =
        callOrError(api.modifyTripToFavouritesAsync(mapOfHeaders, trip.trip.id, trip))

    override suspend fun modifyLike(tripId: Int, trip: TripLikeRequest): Flow<Resource<TripsResponseItem>> =
        callOrError(api.modifyLikeAsync(mapOfHeaders, tripId, trip))

    override suspend fun unsubscribeOnTrip(subscribeOnTripRequest: UnsubscribeOnTripRequest, journeyId: Int)
            : Flow<Resource<SubscribeOnTripResponse>> =
        callOrError(api.unsubscribeOnTripAsync(mapOfHeaders, subscribeOnTripRequest, journeyId = journeyId))
}