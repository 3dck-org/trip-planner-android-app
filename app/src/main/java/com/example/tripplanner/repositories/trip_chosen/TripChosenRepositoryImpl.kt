package com.example.tripplanner.repositories.trip_chosen

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.*
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripChosenRepositoryImpl @Inject constructor(private val api: TripPlannerAPI) :
    BaseRepository(), ITripChosenRepository {

    override suspend fun getTripsById(tripId: Int): Flow<Resource<TripByIdResponse>> =
        callOrError(api.getTripByIdAsync(mapOfHeaders, tripId))

    override suspend fun modifyFavoriteTrip(
        trip: Trips
    ): Flow<Resource<TripsResponseItem>> =
        callOrError(api.modifyTripToFavouritesAsync(mapOfHeaders, trip.trip.id, trip))

    override suspend fun getCurrentJourney(): Flow<Resource<CurrentJourneyResponse>> =
        callOrError(api.getCurrentJourneyAsync(mapOfHeaders))
}