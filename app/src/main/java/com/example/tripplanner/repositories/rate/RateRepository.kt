package com.example.tripplanner.repositories.rate

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.RatingRequest
import com.example.tripplanner.domain.RatingResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RateRepository @Inject constructor(private val api: TripPlannerAPI) :
    IRateRepository, BaseRepository() {

    override suspend fun rateTrip(rateRequest: RatingRequest): Flow<Resource<RatingResponse>> {
        return callOrError(api.rateTrip(mapOfHeaders, rateRequest))
    }
}