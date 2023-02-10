package com.example.tripplanner.repositories.change_password

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.domain.PasswordRequest
import com.example.tripplanner.domain.PasswordResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePasswordRepositoryImpl @Inject constructor(private val api: TripPlannerAPI) :
    IChangePasswordRepository, BaseRepository() {

    override suspend fun changePassword(passwordRequest: PasswordRequest): Flow<Resource<PasswordResponse>> =
        callOrError(api.changePasswordAsync(mapOfHeaders, passwordRequest))
}