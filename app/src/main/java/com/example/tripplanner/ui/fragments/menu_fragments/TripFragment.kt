package com.example.tripplanner.ui.fragments.menu_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.ui.adapters.PlaceAdapter
import com.example.tripplanner.databinding.FragmentTripBinding
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.TripByIdResponse
import com.example.tripplanner.extensions.*
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.utils.Converter
import com.example.tripplanner.utils.GlideLoader
import com.example.tripplanner.view_models.TripViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class TripFragment : Fragment() {

    private val menuActivityInstance by lazy { activity as MenuActivity }
    private lateinit var viewBinding: FragmentTripBinding
    private val tripViewModel: TripViewModel by viewModels()
    private var adapter: PlaceAdapter? = null
    private var trip: TripByIdResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavBar()
        getTripId()
        initViewBinding()
        initRecyclerView()
        collectTripById()
        collectLikes()
        collectCurrentTrip()
        collectSubscriptionResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getTripById()
        getCurrentTrip()
        return viewBinding.root
    }

    private fun initViewBinding() {
        viewBinding = FragmentTripBinding.inflate(layoutInflater)
    }

    private fun collectTripById() {
        lifecycleScope.launch {
            tripViewModel.responseTrip.collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        withContext(Dispatchers.Main) {
                            viewBinding.progressBar.hide()
                            setData(it.data)
                            adapter?.addData(it.data.trip_place_infos)
                        }
                        trip = it.data
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress")
                        viewBinding.layoutTrip.root.hide()
                        viewBinding.progressBar.show()
                    }
                    is Resource.Empty -> {
                        Timber.d("Empty")
                    }
                    is Resource.Error -> {
                        Timber.d("Error ${it.errorData}")
                    }
                }
            }
        }
    }

    private fun collectLikes() {
        lifecycleScope.launch {
            tripViewModel.responseFavourite.collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        setLikeState(isLiked = it.data.isFavourite)
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress")
                    }
                    is Resource.Empty -> {
                        Timber.d("Empty")
                    }
                    is Resource.Error -> {
                        Timber.d("Error ${it.errorData}")
                    }
                }
            }
        }
    }

    private fun collectSubscriptionResponse() {
        lifecycleScope.launchWhenResumed {
            tripViewModel.responseSubscribeOnTrip.collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        with(viewBinding.layoutTrip) {
                            layoutNoteStart.root.makeGone()
                            layoutFailedStart.root.makeGone()
                            btnAction.makeGone()
                            layoutSuccessStart.root.makeVisible()
                        }
                    }
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData}")
                        with(viewBinding.layoutTrip) {
                            layoutNoteStart.root.makeGone()
                            layoutSuccessStart.root.makeGone()
                            btnAction.makeGone()
                            layoutFailedStart.root.makeVisible()
                        }
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress: ${it.data}")
                    }
                }
            }
        }
    }

    private fun collectCurrentTrip() {
        lifecycleScope.launch {
            tripViewModel.responseCurrentTrip.collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data}")
                        viewBinding.layoutTrip.btnAction.makeGone()
                        viewBinding.layoutTrip.layoutNoteStart.apply {
                            root.makeVisible()
                            tvState.text = "Information"
                            tvStateDescription.text = "Hey, to start a new trip, please, end the current journey"
                        }
//                        viewBinding.layoutTrip.layoutNoteStart.root.makeGone()
                    }
                    is Resource.Empty -> {
                        Timber.d("Empty")
                        viewBinding.layoutTrip.btnAction.apply {
                            makeVisible()
                            text = "Start"
                        }
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress")
                    }
                    is Resource.Error -> {
                        Timber.d("Error ${it.errorData}")
                    }
                }
            }
        }
    }

    private fun setLikeState(isLiked: Boolean) {
        if (isLiked) {
            viewBinding.layoutTrip.tripLikeBtn.setImageResource(R.drawable.ic_like_filled)
        } else {
            viewBinding.layoutTrip.tripLikeBtn.setImageResource(R.drawable.ic_like)
        }
    }

    private fun setData(trip: TripByIdResponse) {
        with(viewBinding.layoutTrip) {
            root.show()
            titleTripTv.text = trip.name
            durationTv.text = Converter.convertDuration(trip.duration)
            distanceTv.text = Converter.convertDistance(trip.distance.toFloat())
            descriptionTripTv.text = trip.description
            createdAtTv.text = trip.created_at.formatDate()
            userNameTv.text = "${trip.user.name} ${trip.user.surname}"
            recyclerView.isNestedScrollingEnabled = false
            tripLikeBtn.setOnClickListener {
                tripViewModel.modifyFavoriteTrip(trip.tripModelWithChangedLike(tripViewModel.isLiked))
            }
            btnAction.setOnClickListener {
                tripViewModel.subscribeOnTrip(trip.id)
            }
            fabBack.setOnClickListener {
                findNavController().navigate(R.id.tripsListFragment)
            }
            setLikeState(trip.favorite.toBoolean())
        }
        context?.let {
            GlideLoader.loadImage(it, viewBinding.layoutTrip.tripIv, trip.image_url)
        }
    }

    private fun getCurrentTrip() {
        tripViewModel.getCurrentJourney()
    }

    private fun getTripById() {
        tripViewModel.getTripById()
    }

    private fun getTripId() {
        tripViewModel.tripId = this.arguments?.getInt("tripId") ?: 0
    }

    private fun String.formatDate() = this.split("T")[0]

    private fun initRecyclerView() {
        adapter = PlaceAdapter()
        val llm = LinearLayoutManager(activity?.baseContext)
        llm.orientation = RecyclerView.VERTICAL
        viewBinding.layoutTrip.recyclerView.adapter = adapter
        viewBinding.layoutTrip.recyclerView.layoutManager = llm
    }

    private fun hideBottomNavBar() {
        menuActivityInstance.hideMenu()
    }
}