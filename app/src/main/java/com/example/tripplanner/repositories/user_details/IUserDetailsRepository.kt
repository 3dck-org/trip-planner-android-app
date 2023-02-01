package com.example.tripplanner.repositories.user_details

import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.UserDetails
import kotlinx.coroutines.flow.Flow

interface IUserDetailsRepository {
    suspend fun getUserDetails(): Flow<Resource<UserDetails>>
}