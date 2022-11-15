package com.example.tripplanner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.Trips
import com.example.tripplanner.models.TripsResponseItem
import com.example.tripplanner.repositories.likes.PutLikeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikesViewModel @Inject constructor(
    val likeRepository: PutLikeRepository
) : ViewModel() {

    private val _responseFavourite =
        MutableStateFlow<Resource<TripsResponseItem>>(Resource.Progress())
    val responseFavourite: StateFlow<Resource<TripsResponseItem>>
        get() = _responseFavourite

    fun modifyFavoriteTrip(trip: Trips) {
        viewModelScope.launch(Dispatchers.IO) {
            likeRepository.modifyFavoriteTrip(trip.trip.id, trip)
                .collect { _responseFavourite.emit(it) }
        }
    }
}