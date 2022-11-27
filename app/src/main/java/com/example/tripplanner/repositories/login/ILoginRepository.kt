package com.example.tripplanner.repositories.login

import com.example.tripplanner.models.LoginRequest
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<Resource<OauthResponse>>
    suspend fun getUserDetails(): Flow<Resource<UserDetails>>
}