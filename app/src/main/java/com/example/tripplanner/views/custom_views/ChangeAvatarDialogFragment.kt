package com.example.tripplanner.views.custom_views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tripplanner.databinding.ChangeUsersAvatarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeAvatarDialogFragment : DialogFragment() {

    lateinit var binding: ChangeUsersAvatarBinding
    var isAvatarURLCorrect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        setCorners()
        provideNewUrl()
        return binding.root
    }

    private fun initBinding() {
        binding = ChangeUsersAvatarBinding.inflate(layoutInflater)
    }

    private fun showProgress() {

    }

    private fun provideNewUrl() {
        binding.updateAvatarBtn.setOnClickListener {
            getImageFromUrlWithException(binding.avatarUrlEt.text.toString())
            if(isAvatarURLCorrect){
                binding.avatarUrlIl.isErrorEnabled = false
            }else{
                binding.avatarUrlIl.isErrorEnabled = true
                binding.avatarUrlIl.error = "Image is not valid"
            }
        }
    }

    private fun hideProgress() {}

    private fun setCorners() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun getImageFromUrlWithException(imageUrl: String?) {
        activity?.let {
            Glide.with(it.baseContext)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        isAvatarURLCorrect = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        isAvatarURLCorrect = true
                        return false
                    }
                })
                .submit()
        }
    }
}