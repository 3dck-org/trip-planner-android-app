package com.example.tripplanner.views.custom_views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ChangeUsersAvatarBinding
import com.example.tripplanner.extensions.getImageFromURL
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserAvatarRequest
import com.example.tripplanner.viewmodels.AvatarsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL


@AndroidEntryPoint
class ChangeAvatarDialogFragment(private val imageView: ImageView) : DialogFragment() {

    lateinit var binding: ChangeUsersAvatarBinding
    var isAvatarURLCorrect = false
    private val viewModel: AvatarsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        listenForOauth()
        setCorners()
        provideNewUrl()
        return binding.root
    }

    private fun initBinding() {
        binding = ChangeUsersAvatarBinding.inflate(layoutInflater)
    }

    private fun showProgress() = binding.progressBar.makeVisible()


    private fun listenForOauth() {
        lifecycleScope.launchWhenResumed {
            viewModel.response.collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData.error}")
                        hideProgress()
                    }
                    is Resource.Progress -> {
//                        showProgress()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        activity?.let { activity ->
                            getImageFromURL(
                                binding.avatarUrlEt.text.toString(),
                                activity.applicationContext,
                                imageView
                            )
                        }
                        viewModel.setNewUrl(
                            it.data.image_url
                        )
                        dismiss()
                    }
                }
            }
        }
    }
    private fun getImageFromURLCheck(imageUrl: String?, context: Context, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.ic_godlen_city)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
            .submit()
    }

    private fun provideNewUrl() {
        binding.updateAvatarBtn.setOnClickListener {
            try{
                activity?.let {
                    getImageFromURLCheck(
                        binding.avatarUrlEt.text.toString(),
                        it.baseContext,
                        imageView
                    )
                }
                binding.avatarUrlIl.isErrorEnabled = false
                if(binding.avatarUrlEt.text.toString()!="" && URLUtil.isValidUrl(binding.avatarUrlEt.text.toString())) {
                    binding.avatarUrlIl.isErrorEnabled = false
                    viewModel.updateCurrentUser(UserAvatarRequest(binding.avatarUrlEt.text.toString()))
                }
                else{
                    binding.avatarUrlIl.isErrorEnabled = true
                    binding.avatarUrlIl.error="Url format is incorrect"
                }
            }catch (e: Exception){
                binding.avatarUrlIl.isErrorEnabled = true
                binding.avatarUrlIl.error="Url format is incorrect"
            }

        }
    }

    private fun hideProgress() = binding.progressBar.makeGone()

    private fun setCorners() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}