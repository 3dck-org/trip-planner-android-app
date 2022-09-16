package com.example.tripplanner.views.fragments.menufragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.adapters.OfferedTripsAdapter
import com.example.tripplanner.databinding.FragmentTripsBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponseItem
import com.example.tripplanner.viewmodels.JourneysViewModel
import com.example.tripplanner.viewmodels.OfferedTripsViewModel
import com.example.tripplanner.viewmodels.SubscribeOnTripViewModel
import com.example.tripplanner.views.dialogs.TripSubscriptionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.time.LocalDateTime

@AndroidEntryPoint
class TripsFragment : Fragment() {

    private lateinit var binding: FragmentTripsBinding
    private var offeredTripsAdapter: OfferedTripsAdapter? = null
    private val tripsViewModel: OfferedTripsViewModel by viewModels()
    private val journeysViewModel: JourneysViewModel by viewModels()
    private var activeJourneyId: Int = -1
    private val subscriptionViewModel: SubscribeOnTripViewModel by viewModels()

    private fun refreshOnDragUp() {
        binding.swipeContainer.setOnRefreshListener {
            getJourneys()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewBinding()
        initRecyclerView()
        listenForTrips()
        listenForJourneys()
        getJourneys()
        refreshOnDragUp()
        return binding.root
    }

    private fun getTrips() = tripsViewModel.getTrips()

    private fun getJourneys() = journeysViewModel.getJourneys()

    private fun listenForTrips() {
        lifecycleScope.launchWhenResumed {
            tripsViewModel.response.collect {
                with(Dispatchers.Main) {
                    binding.swipeContainer.isRefreshing = false
                }
                when (it) {
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData}")
                    }
                    is Resource.Progress -> {
                        binding.progressBar.show()
                    }
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        binding.progressBar.hide()
                        Timber.d("!!!! ${activeJourneyId} ${it.data}")
                        offeredTripsAdapter?.addDataToAdapter(it.data, activeJourneyId)
                    }
                }
            }
        }
    }

    private fun listenForJourneys() {
        lifecycleScope.launchWhenResumed {
            journeysViewModel.response.collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData}")
                    }
                    is Resource.Progress -> {
                        binding.progressBar.show()
                    }
                    is Resource.Success -> {
                        activeJourneyId =
                            it.data.find { journey -> journey.completed != "true" }?.trip_id ?: -1
                        getTrips()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        offeredTripsAdapter = OfferedTripsAdapter(::showSubscriptionOption)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        binding.recyclerView.adapter = offeredTripsAdapter
        binding.recyclerView.layoutManager = llm
    }

    private fun initViewBinding() {
        binding = FragmentTripsBinding.inflate(layoutInflater)
    }

    private fun collectSubscriptionResponse() {
        lifecycleScope.launchWhenResumed {
            subscriptionViewModel.response.collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData}")
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress: ${it.data}")
                    }
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        getJourneys()
                    }
                }
            }
        }
    }

    fun showSubscriptionOption(trip: TripsResponseItem) {
        activity?.let {
            TripSubscriptionDialog(trip, ::collectSubscriptionResponse) {
                subscriptionViewModel.subscribeOnTrip(trip.id, LocalDateTime.now().toString())
            }.show(it.supportFragmentManager, "Tag")
        }
    }
}