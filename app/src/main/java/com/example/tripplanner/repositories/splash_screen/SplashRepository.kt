package com.example.tripplanner.repositories.splash_screen

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SplashRepository @Inject constructor(private val api: TripPlannerAPI) :
    ISplashRepository, BaseRepository() {

    override suspend fun getUserDetails(): Flow<Resource<UserDetails>> =
        callOrError(api.getUsersDetails(BaseRepository.mapOfHeaders))
}