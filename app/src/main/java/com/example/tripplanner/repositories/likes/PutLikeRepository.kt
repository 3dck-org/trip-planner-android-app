package com.example.tripplanner.repositories.likes

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.Trips
import com.example.tripplanner.domain.TripsResponseItem
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutLikeRepository @Inject constructor(private val api: TripPlannerAPI) :
    IPutLikeRepository, BaseRepository() {

    override suspend fun modifyFavoriteTrip(
        trip: Trips
    ): Flow<Resource<TripsResponseItem>> =
        callOrError(api.modifyTripToFavouritesAsync(mapOfHeaders, trip.trip.id, trip))
}