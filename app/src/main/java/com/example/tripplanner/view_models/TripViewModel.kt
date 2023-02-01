package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.domain.*
import com.example.tripplanner.repositories.trip_chosen.TripChosenRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(private val tripRepository: TripChosenRepositoryImpl) :
    ViewModel() {

    private val _responseTrip =
        MutableStateFlow<Resource<TripByIdResponse>>(Resource.Progress())
    val responseTrip: StateFlow<Resource<TripByIdResponse>>
        get() = _responseTrip

    private val _responseFavourite =
        MutableStateFlow<Resource<TripsResponseItem>>(Resource.Progress())
    val responseFavourite: StateFlow<Resource<TripsResponseItem>>
        get() = _responseFavourite

    private val _responseCurrentTrip =
        MutableStateFlow<Resource<CurrentJourneyResponse>>(Resource.Progress())
    val responseCurrentTrip: StateFlow<Resource<CurrentJourneyResponse>>
        get() = _responseCurrentTrip

    private val _responseSubscribeOnTrip =
        MutableStateFlow<Resource<SubscribeOnTripResponse>>(Resource.Progress())
    val responseSubscribeOnTrip: StateFlow<Resource<SubscribeOnTripResponse>>
        get() = _responseSubscribeOnTrip

    var isActiveTrip = false
    var tripId = 0
    var isLiked = false

    fun getTripById() {
        viewModelScope.launch {
            tripRepository.getTripsById(tripId).collect {
                _responseTrip.emit(it)
                if (it is Resource.Success) isLiked = it.data.favorite.toBoolean()
            }
        }
    }

    fun getCurrentJourney() {
        viewModelScope.launch(Dispatchers.IO) {
            tripRepository.getCurrentJourney().collect {
                _responseCurrentTrip.emit(it)
                setIsActiveTrip(it)
            }
        }
    }

    private fun setIsActiveTrip(it: Resource<CurrentJourneyResponse>) {
        isActiveTrip = if (it is Resource.Success) {
            (it.data.trip_id == tripId)
        } else {
            false
        }
    }

    fun subscribeOnTrip(tripId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tripRepository.subscribeOnTrip(
                SubscribeOnTripRequest(
                    trip_id = tripId,
                    start_at = LocalDateTime.now().toString()
                )
            ).collect {
                _responseSubscribeOnTrip.emit(it)
            }
        }
    }

    fun modifyFavoriteTrip(trip: Trips) {
        viewModelScope.launch(Dispatchers.IO) {
            tripRepository.modifyFavoriteTrip(trip)
                .collect {
                    _responseFavourite.emit(it)
                    if (it is Resource.Success) isLiked = it.data.isFavourite
                }
        }
    }
}