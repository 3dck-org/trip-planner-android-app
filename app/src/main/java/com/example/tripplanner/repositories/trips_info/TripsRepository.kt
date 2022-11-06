package com.example.tripplanner.repositories.trips_info

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponse
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripsRepository @Inject constructor(private val api: TripPlannerAPI) :
    ITripsRepository, BaseRepository() {

    override suspend fun getTrips(): Flow<Resource<TripsResponse>> =
        callOrError(api.getTrips(mapOfHeaders))
}
