package com.example.tripplanner.repositories.likes

import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.Trips
import com.example.tripplanner.models.TripsResponseItem
import kotlinx.coroutines.flow.Flow

interface IPutLikeRepository {
    suspend fun modifyFavoriteTrip(tripId: Int, trip: Trips): Flow<Resource<TripsResponseItem>>
}