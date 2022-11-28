package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserAvatarRequest
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.repositories.avatars_update.AvatarsRepository
import com.example.tripplanner.sharedpreferences.UserContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvatarsViewModel @Inject constructor(
    val repository: AvatarsRepository,
    private val userContainer: UserContainer
) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<UserDetails>>(Resource.Progress())
    val response: StateFlow<Resource<UserDetails>>
        get() = _response

    fun updateCurrentUser(user: UserAvatarRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCurrentUser(user).collect(_response)
        }
    }

    fun setNewUrl(url : String?) {
        userContainer.currentUser = getCurrentUser().copy(image_url = url)
    }

    fun getCurrentUser() = userContainer.currentUser
}
