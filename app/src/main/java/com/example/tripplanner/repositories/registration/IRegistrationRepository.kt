package com.example.tripplanner.repositories.registration

import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.RegistrationRequest
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface IRegistrationRepository {
    suspend fun register(request: RegistrationRequest) : Flow<Resource<OauthResponse>>
    suspend fun getUserDetails(): Flow<Resource<UserDetails>>
}