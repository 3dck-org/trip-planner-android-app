package com.example.tripplanner.repositories.login

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.LoginRequest
import com.example.tripplanner.domain.OauthResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: TripPlannerAPI) :
    ILoginRepository, BaseRepository() {

    override suspend fun login(request: LoginRequest): Flow<Resource<OauthResponse>> =
        callOrError(api.loginAsync(request))
}
