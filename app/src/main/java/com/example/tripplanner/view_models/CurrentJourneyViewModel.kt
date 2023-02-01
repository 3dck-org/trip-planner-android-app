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

    var isLiked = false

    fun getCurrentJourney() {
        viewModelScope.launch {
            repository.getCurrentJourney().collect {
                _response.emit(it)
                if (it is Resource.Success) isLiked = it.data.trip.favorite.toBoolean()
            }
        }
    }

    fun modifyFavoriteTrip(trip: Trips) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.modifyFavoriteTrip(trip)
                .collect {
                    _responseFavourite.emit(it)
                    if (it is Resource.Success) isLiked = it.data.isFavourite
                }
        }
    }
}