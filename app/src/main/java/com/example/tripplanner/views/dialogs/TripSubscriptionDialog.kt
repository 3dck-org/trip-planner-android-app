package com.example.tripplanner.views.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.tripplanner.R
import com.example.tripplanner.databinding.DialogTripSubscriptionBinding
import com.example.tripplanner.models.TripsResponseItem

class TripSubscriptionDialog(
    val trip: TripsResponseItem,
    val unitListenForSubsription: () -> Unit,
    val unitSubscribe: () -> Unit
) : AppCompatDialogFragment() {

    private lateinit var binding: DialogTripSubscriptionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViewBinding()
        setCorners()
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCorners()
        showTripInfo(trip)
        unitListenForSubsription.invoke()
        builder.setView(binding.root)
        return builder.create()
    }

    private fun setCorners(){
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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