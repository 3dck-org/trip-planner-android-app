package com.example.tripplanner.ui.dialogs

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
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.Trips
import com.example.tripplanner.domain.TripsResponseItem
import com.example.tripplanner.view_models.LikesViewModel
import com.example.tripplanner.view_models.TripListViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime

@AndroidEntryPoint
class TripSubscriptionDialog(private val closeAction: () -> Unit) : DialogFragment() {

    private lateinit var binding: DialogTripSubscriptionBinding
    private var builder : AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        setCorners()
        builder?.setView(binding.root)
        setButton()
        return binding.root
    }

    private fun setCorners() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setButton(){
        binding.signInBtn.setOnClickListener{
            dismiss()
            closeAction.invoke()
        }
    }

    private fun initViewBinding() {
        binding = DialogTripSubscriptionBinding.inflate(LayoutInflater.from(context))
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        closeAction.invoke()
    }

    override fun onDetach() {
        super.onDetach()
        dismiss()
    }
}