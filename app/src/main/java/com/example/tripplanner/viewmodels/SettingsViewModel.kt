package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tripplanner.sharedpreferences.UserContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val userContainer: UserContainer) : ViewModel() {

    fun getCurrentUser() = userContainer.currentUser
}