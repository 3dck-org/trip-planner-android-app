package com.example.tripplanner.ui.fragments.start_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.databinding.FragmentRegisterBinding
import com.example.tripplanner.extensions.emailLoginValidation
import com.example.tripplanner.extensions.fieldIsNotEmptyValidation
import com.example.tripplanner.extensions.passwordValidation
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.repositories.BaseRepository
import com.example.tripplanner.utils.sharedpreferences.EncryptedSharedPreferences
import com.example.tripplanner.view_models.RegistrationViewModel
import com.example.tripplanner.ui.activities.MenuActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val registrationViewModel: RegistrationViewModel by viewModels()

    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        activateConfirmationButton()
        setConfirmationButton()
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
                turnOffErrors()
            }
            passwordEditText.doAfterTextChanged {
                turnOffErrors()
            }
            nameEditText.doAfterTextChanged {
                turnOffErrors()
            }
            surnnameEditText.doAfterTextChanged {
                turnOffErrors()
            }
            loginEditText.doAfterTextChanged {
                turnOffErrors()
            }
        }
    }

    private fun turnOffErrors() {
        with(binding) {
            emailLayout.error = null
            passwordLayout.error = null
            nameLayout.error = null
            surnameLayout.error = null
            loginLayout.error = null
        }
    }

    private fun setConfirmationButton() =
        binding.confirmButton.setOnClickListener {
            if (checkProvidedContent())
                registerOnClick()
        }

    private fun registerOnClick(): Unit = registrationViewModel.register(
        email = binding.emailEditText.text.toString(),
        password = binding.passwordEditText.text.toString(),
        name = binding.nameEditText.text.toString(),
        surname = binding.surnnameEditText.text.toString(),
        login = binding.loginEditText.text.toString()
    )


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
                        sharedPref.addPreference(Constants.TOKEN, it.data.access_token)
                        sharedPref.addPreference(Constants.REFRESH_TOKEN, it.data.refresh_token)
                        BaseRepository.addToken(it.data.access_token)
                        val intent = Intent(activity, MenuActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun checkProvidedContent(): Boolean =
        with(binding) {
            emailLayout.emailLoginValidation(emailEditText.text.toString())
                .and(passwordLayout.passwordValidation(passwordEditText.text.toString()))
                .and(nameLayout.fieldIsNotEmptyValidation(nameEditText.text.toString()))
                .and(surnameLayout.fieldIsNotEmptyValidation(surnnameEditText.text.toString()))
                .and(loginLayout.fieldIsNotEmptyValidation(loginEditText.text.toString()))
        }
}

