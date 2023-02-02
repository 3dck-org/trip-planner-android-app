package com.example.tripplanner.ui.fragments.menu_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.ui.adapters.TripsAdapter
import com.example.tripplanner.databinding.FragmentTripsListBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.Trips
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.utils.sharedpreferences.EncryptedSharedPreferences
import com.example.tripplanner.view_models.TripListViewModel
import com.example.tripplanner.ui.dialogs.TripSubscriptionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TripsListFragment : Fragment() {

    private val menuActivityInstance by lazy { activity as MenuActivity }
    private lateinit var binding: FragmentTripsListBinding
    private var offeredTripsAdapter: TripsAdapter? = null
    private val tripListViewModel: TripListViewModel by viewModels()
    private var activeJourneyId: Int = -1

    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences

    private fun refreshOnDragUp() {
        binding.swipeContainer.setOnRefreshListener {
            getJourneys()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initRecyclerView()
        collectForTrips()
        collectForJourneys()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getJourneys()
        refreshOnDragUp()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        menuActivityInstance.showMenu()
    }

    private fun getTrips() = tripListViewModel.getTrips()

    private fun getJourneys() = tripListViewModel.getJourneys()

    private fun collectForTrips() {
        lifecycleScope.launch {
            tripListViewModel.response.collect {
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
        lifecycleScope.launch {
            tripListViewModel.responseJourney.collect {
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
        offeredTripsAdapter = TripsAdapter(
            {},
            ::showSubscriptionOption)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        binding.recyclerView.adapter = offeredTripsAdapter
        binding.recyclerView.layoutManager = llm
    }

    private fun initViewBinding() {
        binding = FragmentTripsListBinding.inflate(layoutInflater)
    }

    private fun showSubscriptionOption(trip: Trips) {
        findNavController().navigate(R.id.tripFragment, bundleOf("tripId" to trip.trip.id))
    }
}