package com.example.tripplanner.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.databinding.FragmentRegisterBinding
import com.example.tripplanner.extensions.emailLoginValidation
import com.example.tripplanner.extensions.fieldIsNotEmptyValidation
import com.example.tripplanner.extensions.passwordValidation
import com.example.tripplanner.models.Resource
import com.example.tripplanner.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val registrationViewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        activateConfirmationButton()
        setConfirmationButton()
        registerOnClick()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        listenForOauth()
    }

    private fun initBinding() {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
    }


    private fun activateConfirmationButton() {
        with(binding) {
            emailEditText.doAfterTextChanged {
                confirmButton.isEnabled = checkProvidedContent()
            }
            passwordEditText.doAfterTextChanged {
                confirmButton.isEnabled = checkProvidedContent()
            }
            nameEditText.doAfterTextChanged {
                confirmButton.isEnabled = checkProvidedContent()
            }
            surnnameEditText.doAfterTextChanged {
                confirmButton.isEnabled = checkProvidedContent()
            }
        }
    }

    private fun setConfirmationButton(): Unit =
        binding.confirmButton.setOnClickListener { registerOnClick() }

    private fun registerOnClick(): Unit = binding.confirmButton.setOnClickListener {
        registrationViewModel.register(
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            name = binding.nameEditText.text.toString(),
            surname = binding.surnnameEditText.text.toString(),
            login = binding.loginEditText.text.toString()
        )
    }

    private fun listenForOauth() {
        lifecycleScope.launchWhenResumed {
            registrationViewModel.response.collect {
                when (it) {
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData}")
                    }
                    is Resource.Progress -> {
                        Timber.d("Progress")
                    }
                    is Resource.Success -> {
                        Timber.d("Success")
                    }
                }
            }
        }
    }

    private fun checkProvidedContent(): Boolean {
        return binding.emailLayout.emailLoginValidation(binding.emailEditText.text.toString()) &&
                binding.passwordLayout.passwordValidation(binding.passwordEditText.text.toString()) && binding.nameLayout.fieldIsNotEmptyValidation(
            binding.nameEditText.text.toString()
        ) && binding.surnameLayout.fieldIsNotEmptyValidation(
            binding.surnnameEditText.text.toString()
        )
    }

}
