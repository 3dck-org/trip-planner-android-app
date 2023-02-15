package com.example.tripplanner.ui.fragments.menu_fragments

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.ui.adapters.TripsAdapter
import com.example.tripplanner.databinding.FragmentTripsListBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.Trips
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
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

    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences

    private val menuActivityInstance by lazy { activity as MenuActivity }
    private lateinit var binding: FragmentTripsListBinding
    private var offeredTripsAdapter: TripsAdapter? = null
    private val tripListViewModel: TripListViewModel by viewModels()

    private val menuProvider = object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.filter_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when(menuItem.itemId){
                R.id.searchItem ->{
                    showFilters()
                    true
                }
                else -> true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initRecyclerView()
        collectForTrips()
        refreshOnDragUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getJourneys()
        tripListViewModel.updateDB()
        addMenuProvider()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        menuActivityInstance.expandBottomNavigationBar()
        menuActivityInstance.showMenu()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeMenuProvider()
    }

    private fun menuHost(): MenuHost{
        return requireActivity()
    }

    private fun addMenuProvider(){
        menuHost().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    private fun removeMenuProvider(){
        menuHost().removeMenuProvider(menuProvider)
    }

    private fun refreshOnDragUp() {
        binding.swipeContainer.setOnRefreshListener {
            getJourneys()
        }
    }

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
                        if(it.data.isEmpty()){
                            binding.tvEmpty.makeVisible()
                        }else{
                            binding.tvEmpty.makeGone()
                        }
                        offeredTripsAdapter?.addDataToAdapter(it.data, tripListViewModel.currentTripId)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        offeredTripsAdapter = TripsAdapter(
            ::navigateToCurrentJourneyFragment,
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

    private fun navigateToCurrentJourneyFragment(){
        NavigationUI.onNavDestinationSelected(menuActivityInstance.bottonNavigationBar().menu.findItem(R.id.historyTripsFragment),findNavController())
    }

    private fun showFilters() {
        findNavController().navigate(R.id.filterFragment)
    }
}