package com.example.tripplanner.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.databinding.ActivityUserBinding
import com.example.tripplanner.extensions.getImageFromURL
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
import com.example.tripplanner.models.Resource
import com.example.tripplanner.models.UserDetails
import com.example.tripplanner.viewmodels.UserDetailsViewModel
import com.example.tripplanner.views.custom_views.ChangeAvatarDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val viewModel: UserDetailsViewModel by viewModels()

    private fun initBinding() {
        binding = ActivityUserBinding.inflate(layoutInflater)
    }

    private fun onAvatarClickEvent(){
        binding.avatarCv.setOnClickListener {
            ChangeAvatarDialogFragment().show(supportFragmentManager,"TAG")
        }
    }

    private fun collectUserDetailsData() {
        lifecycleScope.launch {
            viewModel.response.collect {
                when (it) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            binding.progressBar.makeGone()
                            setAllInfo(it.data)
                        }
                    }
                    is Resource.Progress -> {
                        binding.progressBar.makeVisible()
                    }
                    is Resource.Error -> {
                        Timber.d(it.errorData.error.message)
                    }
                }
            }
        }
    }

    private fun setAllInfo(userDetails: UserDetails) {
        with(binding) {
            titleTv.makeVisible()
            changePasswordBtn.makeVisible()
            nameTv.text = userDetails.name + " " + userDetails.surname
            loginTv.text = "(" + userDetails.login + ")"
            emailTv.text = userDetails.email
            getImageFromURL(userDetails.image_url, applicationContext, binding.avatarIv)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(binding.root)
        collectUserDetailsData()
        viewModel.getUserDetails()
        onAvatarClickEvent()
    }
}