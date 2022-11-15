package com.example.tripplanner.views.dialogs

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.R
import com.example.tripplanner.databinding.DialogTripSubscriptionBinding
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.Trips
import com.example.tripplanner.models.TripsResponseItem
import com.example.tripplanner.viewmodels.LikesViewModel
import com.example.tripplanner.viewmodels.SubscribeOnTripViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime

@AndroidEntryPoint
class TripSubscriptionDialog(val func: () -> Unit) : DialogFragment() {

    var currentTrip: Trips? = null

    private val subscribeSharedViewModel: SubscribeOnTripViewModel by viewModels()
    private val likesSharedViewModel: LikesViewModel by viewModels()

    private lateinit var binding: DialogTripSubscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        collectSubscriptionResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        setCorners()
        Timber.d("***1 ${currentTrip?.trip}")
        currentTrip?.let { showTripInfo(it.trip) }
        builder.setView(binding.root)
        return binding.root
    }

    private fun setCorners() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun showTripInfo(trip: TripsResponseItem) {
        with(binding) {
            tripNameTv.text = "Trip: ${trip.name}"
            tripDescriptionTv.text = "Description: ${trip.description}"
            tripDurationTv.text = "Duration: ${trip.duration} min"
            tripLengthTv.text = "Length: ${trip.distance}km"
            tripLikeBtn.changeIconTint(trip.isFavourite)
            signInBtn.setOnClickListener {
                subscribeSharedViewModel.subscribeOnTrip(
                    trip.id,
                    LocalDateTime.now().toString()
                )
            }
            tripLikeBtn.setOnClickListener {
                currentTrip?.let { curTrip ->
                    likesSharedViewModel.modifyFavoriteTrip(
                        curTrip.copy(
                            curTrip.trip.copy(
                                isFavourite = !curTrip.trip.isFavourite
                            )
                        )
                    )
                }
            }
        }
    }

    private fun collectSubscriptionResponse() {
        lifecycleScope.launchWhenStarted {
            likesSharedViewModel.responseFavourite.collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData}")
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress: ${it.data}")
                    }
                    is Resource.Success -> {
                        Timber.d("Success subs: ${it.data}")
                        currentTrip = Trips(it.data)
                        currentTrip?.let { it1 -> binding.tripLikeBtn.changeIconTint(it1.trip.isFavourite) }
                        func.invoke()
                    }
                }
            }
        }
    }

    private fun MaterialButton.changeIconTint(isTripLiked: Boolean = false) {
        if (isTripLiked)
            this.setIconResource(R.drawable.ic_like_filled)
        else
            this.setIconResource(R.drawable.ic_like)
    }

    private fun initViewBinding() {
        binding = DialogTripSubscriptionBinding.inflate(LayoutInflater.from(context))
    }

    override fun onDestroy() {
        super.onDestroy()
        currentTrip = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDestroy()
    }
}