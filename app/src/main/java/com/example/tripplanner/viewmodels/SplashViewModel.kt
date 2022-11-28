package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.extensions.log
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.splash_screen.SplashRepository
import com.example.tripplanner.sharedpreferences.UserContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val userContainer: UserContainer,
    val splashRepository: SplashRepository
) : ViewModel() {

    private val _responseUserDetails =
        MutableStateFlow<Resource<UserDetails>>(Resource.Progress())
    val responseUserDetails: StateFlow<Resource<UserDetails>>
        get() = _responseUserDetails

    fun getCurrentUser() = userContainer.currentUser

    fun initUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            splashRepository.getUserDetails().collect {
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