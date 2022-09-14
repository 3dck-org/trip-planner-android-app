package com.example.tripplanner.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.databinding.DialogTripSubscriptionBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponseItem
import com.example.tripplanner.viewmodels.OfferedTripsViewModel
import com.example.tripplanner.viewmodels.SubscribeOnTripViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime

@AndroidEntryPoint
class TripSubscriptionDialog(val trip : TripsResponseItem) : AppCompatDialogFragment() {

    private val subscriptionViewModel: SubscribeOnTripViewModel by viewModels()
    private lateinit var binding: DialogTripSubscriptionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViewBinding()
        val builder = AlertDialog.Builder(context)
        showTripInfo(trip)
        collectSubscriptionResponse()
        builder.setView(binding.root)
        return builder.create()
    }

    private fun showTripInfo(trip: TripsResponseItem){
        with(binding){
            tripNameTv.text = "Trip: ${trip.name}"
            tripDescriptionTv.text="Description: ${trip.description}"
            tripDurationTv.text = "Duration: ${trip.duration}"
            tripLengthTv.text = "Length: ${trip.distance}"
            signInBtn.setOnClickListener {
                subscriptionViewModel.subscribeOnTrip(trip.id, LocalDateTime.now().toString())
            }
        }
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
                    }
                }
            }
        }
    }

    private fun initViewBinding() {
        binding = DialogTripSubscriptionBinding.inflate(LayoutInflater.from(context))
    }
}