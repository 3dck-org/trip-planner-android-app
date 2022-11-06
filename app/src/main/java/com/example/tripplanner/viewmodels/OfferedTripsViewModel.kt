package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponse
import com.example.tripplanner.repositories.trips_info.TripsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferedTripsViewModel @Inject constructor(val repository: TripsRepository) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<TripsResponse>>(Resource.Progress())
    val response: StateFlow<Resource<TripsResponse>>
        get() = _response

    fun getTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTrips().collect { _response.emit(it) }
        }
    }

}