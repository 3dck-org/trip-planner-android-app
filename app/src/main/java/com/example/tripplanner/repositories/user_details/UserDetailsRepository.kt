package com.example.tripplanner.repositories.user_details

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.PasswordRequest
import com.example.tripplanner.domain.PasswordResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.UserDetails
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(private val api: TripPlannerAPI) :
    IUserDetailsRepository, BaseRepository() {

    override suspend fun getUserDetails(): Flow<Resource<UserDetails>> =
        callOrError(api.getUsersDetailsAsync(mapOfHeaders))

    override suspend fun changePassword(passwordRequest: PasswordRequest): Flow<Resource<PasswordResponse>> =
        callOrError(api.changePasswordAsync(mapOfHeaders, passwordRequest))
}