package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.domain.PasswordRequest
import com.example.tripplanner.domain.PasswordResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.UserDetails
import com.example.tripplanner.repositories.user_details.UserDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(val repository: UserDetailsRepository) :
    ViewModel() {

    private val _response =
        MutableStateFlow<Resource<UserDetails>>(Resource.Progress())
    val response: StateFlow<Resource<UserDetails>>
        get() = _response

    private val _responseChangePassword =
        MutableStateFlow<Resource<PasswordResponse>>(Resource.Progress())
    val responseChangePassword: StateFlow<Resource<PasswordResponse>>
        get() = _responseChangePassword

    fun getUserDetails() {
        viewModelScope.launch {
            repository.getUserDetails().collect { _response.emit(it) }
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            repository.changePassword(
                PasswordRequest(
                    new_password = newPassword,
                    old_password = oldPassword
                )
            ).collect {
                _responseChangePassword.emit(it)
            }
        }
    }
}