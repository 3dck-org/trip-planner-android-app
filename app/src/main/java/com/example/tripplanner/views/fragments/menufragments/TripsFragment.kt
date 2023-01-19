package com.example.tripplanner.views.fragments.menufragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.adapters.TripsAdapter
import com.example.tripplanner.databinding.FragmentTripsBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.Trips
import com.example.tripplanner.viewmodels.TripsViewModel
import com.example.tripplanner.views.dialogs.TripSubscriptionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class TripsFragment : Fragment() {

    private lateinit var binding: FragmentTripsBinding
    private var offeredTripsAdapter: TripsAdapter? = null
    private val tripsViewModel: TripsViewModel by viewModels()
    private var activeJourneyId: Int = -1

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
        collectForTrips()
        collectForJourneys()
        getJourneys()
        refreshOnDragUp()
        return binding.root
    }

    private fun getTrips() = tripsViewModel.getTrips()

    private fun getJourneys() = tripsViewModel.getJourneys()

    private fun collectForTrips() {
        lifecycleScope.launchWhenResumed {
            tripsViewModel.response.collect {
                withContext(Dispatchers.Main) {
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
                        offeredTripsAdapter?.addDataToAdapter(it.data, activeJourneyId)
                    }
                }
            }
        }
    }

    private fun collectForJourneys() {
        lifecycleScope.launchWhenResumed {
            tripsViewModel.responseJourney.collect {
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
        offeredTripsAdapter = TripsAdapter(::showSubscriptionOption)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        binding.recyclerView.adapter = offeredTripsAdapter
        binding.recyclerView.layoutManager = llm
    }

    private fun initViewBinding() {
        binding = FragmentTripsBinding.inflate(layoutInflater)
    }

    private fun showSubscriptionOption(trip: Trips) {
        TripSubscriptionDialog(::getJourneys).setTrip(trip).show(childFragmentManager, "TAG")
    }
}

private fun TripSubscriptionDialog.setTrip(trip: Trips): TripSubscriptionDialog {
    this.currentTrip = trip
    return this
}
