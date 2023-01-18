package com.example.tripplanner.repositories.current_journey

import com.example.tripplanner.models.CurrentJourneyResponse
import com.example.tripplanner.models.Resource
import kotlinx.coroutines.flow.Flow

interface ICurrentJourneyRepository {
    suspend fun getCurrentJourney() : Flow<Resource<CurrentJourneyResponse>>
}