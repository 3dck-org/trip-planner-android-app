package com.example.tripplanner.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.databinding.DialogTripSubscriptionBinding
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.TripsResponseItem
import com.example.tripplanner.viewmodels.SubscribeOnTripViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

class TripSubscriptionDialog(
    val trip: TripsResponseItem,
    val unitListenForSubsription: () -> Unit,
    val unitSubscribe: () -> Unit
) : AppCompatDialogFragment() {

    private lateinit var binding: DialogTripSubscriptionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViewBinding()
        val builder = AlertDialog.Builder(context)
        showTripInfo(trip)
        unitListenForSubsription.invoke()
        builder.setView(binding.root)
        return builder.create()
    }

    private fun showTripInfo(trip: TripsResponseItem) {
        with(binding) {
            tripNameTv.text = "Trip: ${trip.name}"
            tripDescriptionTv.text = "Description: ${trip.description}"
            tripDurationTv.text = "Duration: ${trip.duration}"
            tripLengthTv.text = "Length: ${trip.distance}"
            signInBtn.setOnClickListener {
                unitSubscribe.invoke()
            }
        }
    }

    private fun initViewBinding() {
        binding = DialogTripSubscriptionBinding.inflate(LayoutInflater.from(context))
    }
}