package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.domain.*
import com.example.tripplanner.repositories.trips_info.TripsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val tripsRepository: TripsRepository
) : ViewModel() {

    var currentTripId = -1

    private val _responseTrips =
        MutableStateFlow<Resource<TripsResponse>>(Resource.Progress())
    val response: StateFlow<Resource<TripsResponse>>
        get() = _responseTrips

    private fun getTrips(idTrip: Int = -1) {
        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.getTrips().collect {
                if (it is Resource.Success) {
                    it.data.apply {
                        sortBy { trip -> trip.isFavourite }
                        sortBy { trip -> trip.id == idTrip }
                    }
                }
                _responseTrips.emit(it)
            }
        }
    }

    fun getJourneys() {
        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.getCurrentJourney().collect {
                when (it) {
                    is Resource.Success -> {
                        currentTripId = it.data.trip_id
                        getTrips(it.data.trip_id)
                    }
                    is Resource.Empty -> {
                        currentTripId = -1
                        getTrips()
                    }
                    else -> {
                        Timber.d("Other answer::::")
                    }
                }
            }
        }
    }
}