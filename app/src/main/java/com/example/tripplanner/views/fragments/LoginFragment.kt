package com.example.tripplanner.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.tripplanner.databinding.FragmentLoginBinding
import com.example.tripplanner.extensions.emailLoginValidation
import com.example.tripplanner.extensions.passwordValidation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        activateConfirmationButton()
        return binding.root
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

}