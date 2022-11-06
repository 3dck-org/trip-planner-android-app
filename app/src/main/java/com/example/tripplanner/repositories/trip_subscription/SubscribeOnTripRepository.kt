package com.example.tripplanner.repositories.trip_subscription

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.SubscribeOnTripRequest
import com.example.tripplanner.models.SubscribeOnTripResponse
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeOnTripRepository @Inject constructor(private val api: TripPlannerAPI) :
    ISubscribeOnTripRepository, BaseRepository() {

    override suspend fun subscribeOnTrip(subscribeOnTripRequest: SubscribeOnTripRequest)
            : Flow<Resource<SubscribeOnTripResponse>> =
        callOrError(api.subscribeOnTrip(mapOfHeaders, subscribeOnTripRequest))
}