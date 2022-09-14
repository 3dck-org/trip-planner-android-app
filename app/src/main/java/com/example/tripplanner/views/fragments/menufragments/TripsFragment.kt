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
import com.example.tripplanner.adapters.OfferedTripsAdapter
import com.example.tripplanner.databinding.FragmentTripsBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponseItem
import com.example.tripplanner.viewmodels.OfferedTripsViewModel
import com.example.tripplanner.views.dialogs.TripSubscriptionDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TripsFragment : Fragment() {

    private lateinit var binding : FragmentTripsBinding
    private var offeredTripsAdapter : OfferedTripsAdapter? = null
    private val tripsViewModel: OfferedTripsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initRecyclerView()
        listenForTrips()
        getTrips()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun getTrips() = tripsViewModel.getTrips()

    private fun listenForTrips() {
        lifecycleScope.launchWhenResumed {
            tripsViewModel.response.collect {
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
                        offeredTripsAdapter?.addDataToAdapter(it.data)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        offeredTripsAdapter = OfferedTripsAdapter(::showSubscriptionOption)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter = offeredTripsAdapter
    }

    private fun initViewBinding(){
        binding = FragmentTripsBinding.inflate(layoutInflater)
    }

    fun showSubscriptionOption(trip : TripsResponseItem){
        activity?.let { TripSubscriptionDialog(trip).show(it.supportFragmentManager,"Tag") }
    }
}