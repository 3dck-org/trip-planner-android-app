package com.example.tripplanner.repositories.likes

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.Trips
import com.example.tripplanner.models.TripsResponseItem
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutLikeRepository @Inject constructor(private val api: TripPlannerAPI) :
    IPutLikeRepository, BaseRepository() {

    override suspend fun modifyFavoriteTrip(
        tripId: Int,
        trip: Trips
    ): Flow<Resource<TripsResponseItem>> =
        callOrError(api.modifyTripToFavourites(mapOfHeaders, trip.trip.id, trip))
}