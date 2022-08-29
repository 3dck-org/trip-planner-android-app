package com.example.tripplanner.repositories.Login

import com.example.tripplanner.models.LoginRequest
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.Resource
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<Resource<OauthResponse>>
}