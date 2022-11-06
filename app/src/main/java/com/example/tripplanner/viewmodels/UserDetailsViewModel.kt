package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.user_details.UserDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(val repository: UserDetailsRepository) :
    ViewModel() {

    private val _response =
        MutableStateFlow<Resource<UserDetails>>(Resource.Progress())
    val response: StateFlow<Resource<UserDetails>>
        get() = _response

    fun getUserDetails() {
        viewModelScope.launch {
            repository.getUserDetails().collect { _response.emit(it) }
        }
    }
}