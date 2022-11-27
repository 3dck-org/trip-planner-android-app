package com.example.tripplanner.repositories.splash_screen

import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface ISplashRepository {

    suspend fun getUserDetails(): Flow<Resource<UserDetails>>
}