package com.example.tripplanner.repositories.user_details

import com.example.tripplanner.domain.PasswordRequest
import com.example.tripplanner.domain.PasswordResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.UserDetails
import kotlinx.coroutines.flow.Flow

interface IUserDetailsRepository {
    suspend fun getUserDetails(): Flow<Resource<UserDetails>>
    suspend fun changePassword(passwordRequest: PasswordRequest): Flow<Resource<PasswordResponse>>
}