package com.example.tripplanner.repositories.registration

import com.example.tripplanner.domain.OauthResponse
import com.example.tripplanner.domain.RegistrationRequest
import com.example.tripplanner.domain.Resource
import kotlinx.coroutines.flow.Flow

interface IRegistrationRepository {
    suspend fun register(request: RegistrationRequest) : Flow<Resource<OauthResponse>>
}