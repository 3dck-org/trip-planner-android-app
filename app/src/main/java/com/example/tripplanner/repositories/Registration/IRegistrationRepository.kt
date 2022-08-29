package com.example.tripplanner.repositories.Registration

import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.RegistrationRequest
import com.example.tripplanner.models.Resource
import kotlinx.coroutines.flow.Flow

interface IRegistrationRepository {
    suspend fun register(request: RegistrationRequest) : Flow<Resource<OauthResponse>>
}