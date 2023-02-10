package com.example.tripplanner.repositories.change_password

import com.example.tripplanner.domain.*
import kotlinx.coroutines.flow.Flow

interface IChangePasswordRepository {
    suspend fun changePassword(passwordRequest: PasswordRequest): Flow<Resource<PasswordResponse>>
}