package com.example.tripplanner.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.databinding.FragmentLoginBinding
import com.example.tripplanner.extensions.emailLoginValidation
import com.example.tripplanner.extensions.passwordValidation
import com.example.tripplanner.models.Resource
import com.example.tripplanner.sharedpreferences.EncryptedSharedPreferences
import com.example.tripplanner.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        activateConfirmationButton()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        listenForOauth()
        loginOnClick()
    }

    private fun loginOnClick(): Unit = binding.signInBtn.setOnClickListener {
        loginViewModel.login(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
    }

    private fun activateConfirmationButton() {
        with(binding) {
            emailEditText.doAfterTextChanged {
                signInBtn.isEnabled = checkProvidedContent()
            }
            passwordEditText.doAfterTextChanged {
                signInBtn.isEnabled = checkProvidedContent()
            }
        }
    }

    private fun checkProvidedContent(): Boolean {
        return binding.emailLayout.emailLoginValidation(binding.emailEditText.text.toString()) &&
                binding.passwordLayout.passwordValidation(binding.passwordEditText.text.toString())
    }

    private fun initBinding() {
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    private fun listenForOauth() {
        lifecycleScope.launchWhenResumed {
            loginViewModel.response.collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData}")
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress")
                    }
                    is Resource.Success -> {
                        Timber.d("Success:")
                        sharedPref.addPreference(Constants.TOKEN, it.data.access_token)
                    }
                }
            }
        }
    }

}