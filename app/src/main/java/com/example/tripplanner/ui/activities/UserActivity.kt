package com.example.tripplanner.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.databinding.ActivityUserBinding
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.UserDetails
import com.example.tripplanner.view_models.UserDetailsViewModel
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
                        Timber.d("Error: ${it.errorData.error_message}")
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(binding.root)
        collectUserDetailsData()
        viewModel.getUserDetails()
    }
}