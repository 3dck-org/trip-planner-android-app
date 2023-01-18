package com.example.tripplanner.repositories.current_journey

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.CurrentJourneyResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentJourneyRepositoryImpl @Inject constructor(private val api: TripPlannerAPI) :
    ICurrentJourneyRepository, BaseRepository() {

    override suspend fun getCurrentJourney(): Flow<Resource<CurrentJourneyResponse>> =
        callOrError(api.getCurrentJourneyAsync(mapOfHeaders))
}