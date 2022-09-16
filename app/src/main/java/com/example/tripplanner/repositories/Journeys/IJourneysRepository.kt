package com.example.tripplanner.repositories.Journeys

import com.example.tripplanner.models.JourneysResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponse
import kotlinx.coroutines.flow.Flow

interface IJourneysRepository {
    suspend fun getJourneys(): Flow<Resource<JourneysResponse>>
}