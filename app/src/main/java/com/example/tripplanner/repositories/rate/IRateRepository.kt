package com.example.tripplanner.repositories.rate

import com.example.tripplanner.domain.*
import kotlinx.coroutines.flow.Flow

interface IRateRepository {
    suspend fun rateTrip(rateRequest: RatingRequest) : Flow<Resource<RatingResponse>>
}