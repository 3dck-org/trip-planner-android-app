package com.example.tripplanner.repositories.registration

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.RegistrationRequest
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api: TripPlannerAPI) :
    IRegistrationRepository, BaseRepository() {

    override suspend fun register(request: RegistrationRequest): Flow<Resource<OauthResponse>> =
        callOrError(api.register(request))

    override suspend fun getUserDetails(): Flow<Resource<UserDetails>> =
        callOrError(api.getUsersDetails(mapOfHeaders))
}
