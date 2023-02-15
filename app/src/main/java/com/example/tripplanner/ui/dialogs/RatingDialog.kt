package com.example.tripplanner.ui.dialogs

import android.app.AlertDialog
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
import com.example.tripplanner.databinding.DialogRatingBinding
import com.example.tripplanner.domain.RatingRequest
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.view_models.RateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RatingDialog(val idTrip: Int) : DialogFragment() {

    val viewModel: RateViewModel by viewModels()
    private lateinit var binding: DialogRatingBinding
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
        collectRating()
        binding.fea.setOnRatingBarChangeListener { _, rating, _ -> binding.btnConfirm.isEnabled = rating!=0f  }
        setButton()
        return binding.root
    }

    private fun setCorners() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setButton(){
        binding.btnConfirm.setOnClickListener{
            viewModel.rate(RatingRequest(grade = binding.fea.rating.toInt(), trip_id = idTrip))
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun collectRating(){
        lifecycleScope.launch {
            viewModel.response.collect{
                when(it){
                   is Resource.Success ->{ dismiss() }
                    else ->{}
                }
            }
        }
    }

    private fun initViewBinding() {
        binding = DialogRatingBinding.inflate(LayoutInflater.from(context))
    }


    override fun onDetach() {
        super.onDetach()
        dismiss()
    }
}