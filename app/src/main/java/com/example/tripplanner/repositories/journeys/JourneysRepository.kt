package com.example.tripplanner.repositories.journeys

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.JourneysResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JourneysRepository @Inject constructor(private val api: TripPlannerAPI) :
    IJourneysRepository, BaseRepository() {

    override suspend fun getJourneys(): Flow<Resource<JourneysResponse>> =
        callOrError(api.getUsersTrips(mapOfHeaders))
}
