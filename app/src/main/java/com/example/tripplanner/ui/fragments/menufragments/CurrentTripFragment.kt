package com.example.tripplanner.ui.fragments.menufragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.adapters.PlaceAdapter
import com.example.tripplanner.databinding.FragmentCurrentTripBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.models.CurrentJourneyResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.utils.GlideLoader
import com.example.tripplanner.viewmodels.CurrentJourneyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CurrentTripFragment : Fragment() {

    private lateinit var binding: FragmentCurrentTripBinding
    private val viewModel: CurrentJourneyViewModel by viewModels()
    private var adapter: PlaceAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        initRecyclerView()
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
                        binding.progressBar.hide()
                        adapter?.apply {
                            addData(it.data.trip.trip_place_infos)
                        }
                    }
                    is Resource.Progress -> {
                        binding.progressBar.show()
                        Timber.d("***Progress")
                    }
                    is Resource.Error -> {
                        Timber.d("***Error: ${it.errorData}")
                    }
                    is Resource.Empty -> {
                        setLayout(null)
                        binding.progressBar.hide()
                    }
                }
            }
        }
    }

    private fun isCurrentJourneyEmpty(journey: CurrentJourneyResponse?) = journey == null

    private fun setLayout(journey: CurrentJourneyResponse?) {
        if (isCurrentJourneyEmpty(journey)) {
            binding.layoutEmpty.root.show()
            binding.layoutSuccess.root.hide()
        } else {
            binding.layoutEmpty.root.hide()
            binding.layoutSuccess.root.show()
            journey?.let { bindData(it) }
        }
    }

    private fun bindData(journey: CurrentJourneyResponse) {
        with(binding.layoutSuccess) {
            titleTripTv.text = journey.trip.name
            durationTv.text = calculateTime(journey.trip.duration)
            distanceTv.text = "${journey.trip.distance}km"
            descriptionTripTv.text = journey.trip.description
            userNameTv.text = "${journey.user.name} ${journey.user.surname}"
            createdAtTv.text = journey.trip.created_at.formatDate()
        }
        context?.let {
            GlideLoader.loadImage(it, binding.layoutSuccess.tripIv, journey.trip.image_url)
        }
    }

    private fun initRecyclerView() {
        adapter = PlaceAdapter()
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        binding.layoutSuccess.recyclerView.adapter = adapter
        binding.layoutSuccess.recyclerView.layoutManager = llm
    }

    private fun String.formatDate() = this.split("T")[0]

    private fun calculateTime(minTime: Int): String {
        return if (minTime >= 60) {
            if ((minTime % 60) != 0)
                "${minTime / 60}h ${minTime % 60}m"
            else
                "${minTime / 60}h "
        } else {
            "${minTime % 60}m"
        }
    }
}