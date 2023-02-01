package com.example.tripplanner.repositories.trips_info

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.*
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripsRepository @Inject constructor(private val api: TripPlannerAPI) :
    ITripsRepository, BaseRepository() {

    override suspend fun getTrips(): Flow<Resource<TripsResponse>> =
        callOrError(api.getTripsAsync(mapOfHeaders))

    override suspend fun subscribeOnTrip(subscribeOnTripRequest: SubscribeOnTripRequest)
            : Flow<Resource<SubscribeOnTripResponse>> =
        callOrError(api.subscribeOnTripAsync(mapOfHeaders, subscribeOnTripRequest))

    override suspend fun getJourneys(): Flow<Resource<JourneysResponse>> =
        callOrError(api.getUsersTripsAsync(mapOfHeaders))
}
