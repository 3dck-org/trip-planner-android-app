package com.example.tripplanner.repositories.user_details

import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface IUserDetailsRepository {
    suspend fun getUserDetails(): Flow<Resource<UserDetails>>
}