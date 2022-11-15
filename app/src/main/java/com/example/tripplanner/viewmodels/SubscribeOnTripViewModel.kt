package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.SubscribeOnTripRequest
import com.example.tripplanner.models.SubscribeOnTripResponse
import com.example.tripplanner.repositories.trip_subscription.SubscribeOnTripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeOnTripViewModel @Inject constructor(
    val repository: SubscribeOnTripRepository
) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<SubscribeOnTripResponse>>(Resource.Progress())
    val response: StateFlow<Resource<SubscribeOnTripResponse>>
        get() = _response

    fun subscribeOnTrip(tripId: Int, start_at: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.subscribeOnTrip(
                SubscribeOnTripRequest(
                    trip_id = tripId,
                    start_at = start_at
                )
            ).collect { _response.emit(it) }
        }
    }
}