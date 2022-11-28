package com.example.tripplanner.repositories.avatars_update

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserAvatarRequest
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AvatarsRepository @Inject constructor(private val api: TripPlannerAPI) :
IAvatarsRepository, BaseRepository(){

    override suspend fun updateCurrentUser(user: UserAvatarRequest): Flow<Resource<UserDetails>> {
        return callOrError(api.updateCurrentUser(mapOfHeaders, user))
    }
}