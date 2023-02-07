package com.example.tripplanner.ui.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.tripplanner.databinding.DialogGenericBinding

object DialogImpl {

    @SuppressLint("ResourceType")
    fun showDialog(context: Context, dialogInformation: DialogInformation) {
        val binding = DialogGenericBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context)
        dialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(binding.root)

            with(binding){
                title.text = dialogInformation.title
                description.text = dialogInformation.description
                btnAction.text = dialogInformation.positiveBtnText
                btnAction.setOnClickListener {
                    dismiss()
                }
            }
            show()
        }
    }

    data class DialogInformation(
        val title: String,
        val description: String,
        val positiveBtnText: String = "Okay",
        val negativeBtnText: String = "No"
    )
}