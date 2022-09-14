package com.example.tripplanner.repositories.SubscribeOnTrip

import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.SubscribeOnTripRequest
import com.example.tripplanner.models.SubscribeOnTripResponse
import kotlinx.coroutines.flow.Flow

interface ISubscribeOnTripRepository {
    suspend fun subscribeOnTrip(subscribeOnTripRequest: SubscribeOnTripRequest): Flow<Resource<SubscribeOnTripResponse>>
}