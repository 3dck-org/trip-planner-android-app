package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.RegistrationRequest
import com.example.tripplanner.models.Resource
import com.example.tripplanner.repositories.Registration.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(val repository: RegisterRepository) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<OauthResponse>>(Resource.Progress())
    val response: StateFlow<Resource<OauthResponse>>
    get() = _response

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
            ).collect { _response.emit(it) }
        }
    }

}