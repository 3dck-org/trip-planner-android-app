package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.domain.*
import com.example.tripplanner.repositories.current_journey.CurrentJourneyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CurrentJourneyViewModel @Inject constructor(val repository: CurrentJourneyRepositoryImpl) :
    ViewModel() {

    private val _response =
        MutableStateFlow<Resource<CurrentJourneyResponse>>(Resource.Progress())
    val response: StateFlow<Resource<CurrentJourneyResponse>>
        get() = _response

    private val _responseFavourite =
        MutableStateFlow<Resource<TripsResponseItem>>(Resource.Progress())
    val responseFavourite: StateFlow<Resource<TripsResponseItem>>
        get() = _responseFavourite

    private val _responseUnsubscribeOnTrip =
        MutableStateFlow<Resource<SubscribeOnTripResponse>>(Resource.Progress())
    val responseUnsubscribeOnTrip: StateFlow<Resource<SubscribeOnTripResponse>>
        get() = _responseUnsubscribeOnTrip

    private val _responseStatus =
        MutableStateFlow<Resource<StatusResponse>>(Resource.Progress())
    val responseStatus: StateFlow<Resource<StatusResponse>>
        get() = _responseStatus

    var isLiked = false

    fun updateStatus(statusRequest: StatusRequest){
        viewModelScope.launch {
            repository.updateStatus(statusRequest).collect{
                _responseStatus.emit(it)
            }
        }
    }

    fun getCurrentJourney() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentJourney().collect {
                if (it is Resource.Success) {
                    Timber.d("********!@ ${it.data.trip.favorite.toBoolean()}")
                    isLiked = it.data.trip.favorite.toBoolean()
                }
                _response.emit(it)
            }
        }
    }

    fun unsubscribeOnTrip(journeyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.unsubscribeOnTrip(
                UnsubscribeOnTripRequest(
                    end_at = LocalDateTime.now().toString()
                ), journeyId
            ).collect {
                _responseUnsubscribeOnTrip.emit(it)
            }
        }
    }

    fun modifyFavoriteTrip(trip: Trips) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.modifyFavoriteTrip(trip)
                .collect {
                    if (it is Resource.Success) isLiked = it.data.isFavourite
                    _responseFavourite.emit(it)
                }
        }
    }
}