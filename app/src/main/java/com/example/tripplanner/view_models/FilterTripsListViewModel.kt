package com.example.tripplanner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripplanner.domain.FiltersResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.filters.FiltersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterTripsListViewModel @Inject constructor(private val filtersRepository: FiltersRepository) :
    ViewModel() {

    private val _response =
        MutableStateFlow<Resource<FiltersResponse>>(Resource.Progress())
    val response: StateFlow<Resource<FiltersResponse>>
        get() = _response

    fun getFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersRepository.getFilters()
                .collect { _response.emit(it) }
        }
    }
}