package com.example.tripplanner.repositories.login

import com.example.tripplanner.domain.LoginRequest
import com.example.tripplanner.domain.OauthResponse
import com.example.tripplanner.domain.Resource
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<Resource<OauthResponse>>
}