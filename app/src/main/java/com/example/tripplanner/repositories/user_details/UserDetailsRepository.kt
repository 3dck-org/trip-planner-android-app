package com.example.tripplanner.repositories.user_details

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(private val api: TripPlannerAPI) :
    IUserDetailsRepository, BaseRepository() {

    override suspend fun getUserDetails(): Flow<Resource<UserDetails>> =
        callOrError(api.getUsersDetailsAsync(mapOfHeaders))
}