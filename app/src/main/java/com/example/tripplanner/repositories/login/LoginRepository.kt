package com.example.tripplanner.repositories.login

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.models.LoginRequest
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: TripPlannerAPI) :
    ILoginRepository, BaseRepository() {

    override suspend fun login(request: LoginRequest): Flow<Resource<OauthResponse>> =
        callOrError(api.loginAsync(request))
}
