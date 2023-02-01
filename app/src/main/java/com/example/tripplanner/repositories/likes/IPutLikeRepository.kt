package com.example.tripplanner.repositories.likes

import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.Trips
import com.example.tripplanner.domain.TripsResponseItem
import kotlinx.coroutines.flow.Flow

interface IPutLikeRepository {
    suspend fun modifyFavoriteTrip(trip: Trips): Flow<Resource<TripsResponseItem>>
}