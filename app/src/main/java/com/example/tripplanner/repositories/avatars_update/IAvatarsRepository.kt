package com.example.tripplanner.repositories.avatars_update

import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserAvatarRequest
import com.example.tripplanner.models.UserDetails
import kotlinx.coroutines.flow.Flow

interface IAvatarsRepository {
    suspend fun updateCurrentUser(user: UserAvatarRequest): Flow<Resource<UserDetails>>
}