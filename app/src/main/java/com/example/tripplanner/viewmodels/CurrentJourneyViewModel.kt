package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.CurrentJourneyResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.repositories.current_journey.CurrentJourneyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentJourneyViewModel @Inject constructor(val repository: CurrentJourneyRepositoryImpl) :
    ViewModel() {

    private val _response =
        MutableStateFlow<Resource<CurrentJourneyResponse>>(Resource.Progress())
    val response: StateFlow<Resource<CurrentJourneyResponse>>
        get() = _response

    fun getCurrentJourney() {
        viewModelScope.launch {
            repository.getCurrentJourney().collect(_response)
        }
    }
}