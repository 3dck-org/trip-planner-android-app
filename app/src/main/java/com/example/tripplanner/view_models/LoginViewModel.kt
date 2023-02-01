package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.domain.LoginRequest
import com.example.tripplanner.domain.OauthResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: LoginRepository) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<OauthResponse>>(Resource.Progress<OauthResponse>())
    val response: StateFlow<Resource<OauthResponse>>
    get() = _response

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.login(
                LoginRequest(
                    client_id = Constants.CLIENT_ID,
                    client_secret = Constants.SECRET,
                    email = email,
                    grant_type = "password",
                    password = password,
                    refresh_token = null
                )
            ).collect { _response.emit(it) }
        }
    }
}