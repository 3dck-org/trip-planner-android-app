package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.domain.RatingRequest
import com.example.tripplanner.domain.RatingResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.rate.RateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RateViewModel @Inject constructor(private val repository: RateRepository) : ViewModel() {

    private val _response =
        MutableStateFlow<Resource<RatingResponse>>(Resource.Progress())
    val response: StateFlow<Resource<RatingResponse>>
        get() = _response

    fun rate(requestRate: RatingRequest){
        viewModelScope.launch {
            repository.rateTrip(requestRate).collect{
                _response.emit(it)
            }
        }
    }
}