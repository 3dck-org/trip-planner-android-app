package com.example.tripplanner.repositories.journeys

import com.example.tripplanner.models.JourneysResponse
import com.example.tripplanner.models.Resource
import kotlinx.coroutines.flow.Flow

interface IJourneysRepository {
    suspend fun getJourneys(): Flow<Resource<JourneysResponse>>
}