package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.JourneysResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.repositories.journeys.JourneysRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JourneysViewModel @Inject constructor(val repository: JourneysRepository) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<JourneysResponse>>(Resource.Progress())
    val response: StateFlow<Resource<JourneysResponse>>
        get() = _response

    fun getJourneys() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getJourneys().collect(_response)
        }
    }
}