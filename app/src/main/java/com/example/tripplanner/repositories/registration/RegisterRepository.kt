package com.example.tripplanner.repositories.registration

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.OauthResponse
import com.example.tripplanner.domain.RegistrationRequest
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api: TripPlannerAPI) :
    IRegistrationRepository, BaseRepository() {

    override suspend fun register(request: RegistrationRequest): Flow<Resource<OauthResponse>> =
        callOrError(api.registerAsync(request))
}
