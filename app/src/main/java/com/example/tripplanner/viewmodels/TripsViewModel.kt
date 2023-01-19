package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.*
import com.example.tripplanner.repositories.trips_info.TripsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    val tripsRepository: TripsRepository
) : ViewModel() {

    private val _responseTrips =
        MutableStateFlow<Resource<TripsResponse>>(Resource.Progress())
    val response: StateFlow<Resource<TripsResponse>>
        get() = _responseTrips

    private val _responseSubscribeOnTrip =
        MutableStateFlow<Resource<SubscribeOnTripResponse>>(Resource.Progress())
    val responseSubscribeOnTrip: StateFlow<Resource<SubscribeOnTripResponse>>
        get() = _responseSubscribeOnTrip

    private val _responseJourney =
        MutableStateFlow<Resource<JourneysResponse>>(Resource.Progress())
    val responseJourney: StateFlow<Resource<JourneysResponse>>
        get() = _responseJourney

    fun getTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.getTrips().collect { _responseTrips.emit(it) }
        }
    }

    fun subscribeOnTrip(tripId: Int, start_at: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.subscribeOnTrip(
                SubscribeOnTripRequest(
                    trip_id = tripId,
                    start_at = start_at
                )
            ).collect { _responseSubscribeOnTrip.emit(it) }
        }
    }

    fun getJourneys() {
        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.getJourneys().collect(_responseJourney)
        }
    }
}