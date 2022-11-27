package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.extensions.log
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.RegistrationRequest
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.registration.RegisterRepository
import com.example.tripplanner.sharedpreferences.UserContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val repository: RegisterRepository,
    private val userContainer: UserContainer
) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<OauthResponse>>(Resource.Progress())
    val response: StateFlow<Resource<OauthResponse>>
        get() = _response

    private val _responseUserDetails =
        MutableStateFlow<Resource<UserDetails>>(Resource.Progress())
    val responseUserDetails: StateFlow<Resource<UserDetails>>
        get() = _responseUserDetails


    fun register(
        email: String,
        password: String,
        name: String,
        surname: String,
        login: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.register(
                RegistrationRequest(
                    client_id = Constants.CLIENT_ID,
                    email = email,
                    password = password,
                    login = login,
                    name = name,
                    surname = surname,
                    role_code = ""
                )
            ).collect {
                getUserDetails()
                _response.emit(it)
            }
        }
    }

    fun getUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserDetails().collect {
                when (it) {
                    is Resource.Success -> {
                        log("Success: ${it.data}")
                        userContainer.currentUser = it.data
                    }
                    is Resource.Error -> {
                        log("Error: ${it.errorData.error}")
                    }
                    else -> {
                        log("Progress")
                    }
                }
                _responseUserDetails.emit(it)
            }
        }
    }
}