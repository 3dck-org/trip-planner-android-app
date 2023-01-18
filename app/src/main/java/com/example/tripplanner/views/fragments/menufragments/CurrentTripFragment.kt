package com.example.tripplanner.views.fragments.menufragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.databinding.FragmentCurrentTripBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.models.CurrentJourneyResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.viewmodels.CurrentJourneyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CurrentTripFragment : Fragment() {

    private lateinit var binding: FragmentCurrentTripBinding
    private val viewModel: CurrentJourneyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        collectCurrentJourney()
        getCurrentJourney()
        return binding.root
    }

    private fun initBinding() {
        binding = FragmentCurrentTripBinding.inflate(layoutInflater)
    }

    private fun getCurrentJourney() = viewModel.getCurrentJourney()

    private fun collectCurrentJourney() {
        lifecycleScope.launch {
            viewModel.response.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("***Success: ${it.data}")
                        setLayout(it.data)
                    }
                    is Resource.Progress -> {
                        Timber.d("***Progress")
                    }
                    is Resource.Error -> {
                        Timber.d("***Error: ${it.errorData}")
                    }
                }
            }
        }
    }

    private fun isCurrentJourneyEmpty(journey: CurrentJourneyResponse?) = journey == null

    private fun setLayout(journey: CurrentJourneyResponse?) {
        if (isCurrentJourneyEmpty(journey)) {
            binding.emptyLayout.root.show()
            binding.successLayout.root.hide()
        } else {
            binding.emptyLayout.root.hide()
            binding.successLayout.root.show()
            journey?.let { bindData(it) }
        }
    }

    private fun bindData(journey: CurrentJourneyResponse) {
        with(binding.successLayout) {
            tripNameTv.text = journey.trip.name
            tripUserNameTv.text = "(by ${journey.user.name})"
            tripDurationTv.text = "Duration: ${journey.trip.duration.toString()}"
            tripLengthTv.text = "Length: ${journey.trip.distance}"
            tripCategoryTv.text = "Category: ${journey.trip.description}"
        }
    }
}