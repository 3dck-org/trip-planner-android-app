package com.example.tripplanner.ui.fragments.menu_fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.ui.adapters.PlaceAdapter
import com.example.tripplanner.databinding.FragmentCurrentTripBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.domain.CurrentJourneyResponse
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.StatusRequest
import com.example.tripplanner.domain.TripPlaceInfoWithStatus
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.ui.adapters.PlaceCurrentTripAdapter
import com.example.tripplanner.ui.dialogs.TripSubscriptionDialog
import com.example.tripplanner.utils.Converter
import com.example.tripplanner.utils.GlideLoader
import com.example.tripplanner.view_models.CurrentJourneyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class CurrentTripFragment : Fragment() {

    private lateinit var binding: FragmentCurrentTripBinding
    private val viewModel: CurrentJourneyViewModel by viewModels()
    private var adapter: PlaceCurrentTripAdapter? = null
    private val menuActivityInstance by lazy { activity as MenuActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initRecyclerView()
        collectCurrentJourney()
        collectFavChangeResponse()
        collectUnsubscribeResponse()
        collectStatusResponse()
    }

    private fun showDialog() {
        activity?.let {
            TripSubscriptionDialog { findNavController().navigate(R.id.tripsListFragment) }.show(
                it.supportFragmentManager, "TAG"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getCurrentJourney()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        menuActivityInstance.showMenu()
    }

    private fun initBinding() {
        binding = FragmentCurrentTripBinding.inflate(layoutInflater)
    }

    private fun getCurrentJourney() = viewModel.getCurrentJourney()

    private fun updateStatus(statusRequest: StatusRequest) = viewModel.updateStatus(statusRequest)

    private fun collectFavChangeResponse() {
        lifecycleScope.launch {
            viewModel.responseFavourite.collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        setLikeState()
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress")
                    }
                    is Resource.Error -> {
                        Timber.d("Error ${it.errorData}")
                    }
                    else -> {
                        Timber.d("Another answers")
                    }
                }
            }
        }
    }

    private fun collectUnsubscribeResponse() {
        lifecycleScope.launch {
            viewModel.responseUnsubscribeOnTrip.collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        showDialog()
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress")
                    }
                    is Resource.Error -> {
                        Timber.d("Error ${it.errorData}")
                        binding.layoutSuccess.apply {
                            layoutFailedStart.root.makeVisible()
                            layoutNoteStart.root.makeGone()
                        }
                    }
                    else -> {
                        Timber.d("Another answers")
                    }
                }
            }
        }
    }

    private fun collectStatusResponse(){
        lifecycleScope.launch {
            viewModel.responseStatus.collect{
                when(it){
                    is Resource.Success->{ getCurrentJourney() }
                    else ->{ Timber.d("$it") }
                }
            }
        }
    }

    private fun collectCurrentJourney() {
        lifecycleScope.launch {
            viewModel.response.collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("***Success: ${it.data}")
                        withContext(Dispatchers.Main) {
                            setLayout(it.data)
                            setLikeState()
                            binding.progressBar.hide()
                            adapter?.addData(
                                it.data.journey_place_infos
                            )
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
            durationTv.text = Converter.convertDuration(journey.trip.duration)
            distanceTv.text = Converter.convertDistance(journey.trip.distance.toFloat())
            descriptionTripTv.text = journey.trip.description
            userNameTv.text = "${journey.user.name} ${journey.user.surname}"
            createdAtTv.text = journey.trip.created_at.formatDate()
            recyclerView.isNestedScrollingEnabled = false
            setLikeState()
            fabBack.makeGone()
            btnAction.apply {
                makeVisible()
                text = "End journey"
                setOnClickListener {
                    viewModel.unsubscribeOnTrip(journeyId = journey.id)
                }
            }
            tripLikeBtn.setOnClickListener {
                viewModel.modifyFavoriteTrip(
                    journey.mapCurJourneyToTrips(
                        viewModel.isLiked
                    )
                )
            }
            layoutNoteStart.tvStateDescription.text =
                "Hey, there you can end your current trip to start a new one. Good luck"
        }
        context?.let {
            GlideLoader.loadImage(it, binding.layoutSuccess.tripIv, journey.trip.image_url)
        }
    }

    private fun setLikeState() {
        Timber.d("******* ${viewModel.isLiked}")
        if (viewModel.isLiked) {
            binding.layoutSuccess.tripLikeBtn.setImageResource(R.drawable.ic_like_filled)
        } else {
            binding.layoutSuccess.tripLikeBtn.setImageResource(R.drawable.ic_like)
        }
    }

    private fun initRecyclerView() {
        adapter = PlaceCurrentTripAdapter(::updateStatus)
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        binding.layoutSuccess.recyclerView.adapter = adapter
        binding.layoutSuccess.recyclerView.layoutManager = llm
    }

    private fun String.formatDate() = this.split("T")[0]
}