package com.example.tripplanner.ui.fragments.menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.databinding.FragmentUserDetailsBinding
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.UserDetails
import com.example.tripplanner.extensions.*
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.view_models.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private val viewModel: UserDetailsViewModel by viewModels()
    private val menuActivityInstance by lazy { activity as MenuActivity }
    private lateinit var viewBinding: FragmentUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        collectUserDetailsData()
        collectChangePasswordResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        menuActivityInstance.hideMenu()
        setConfirmationButton()
        openChangePasswordLayout()
        activateConfirmationButtonOnFocus()
        viewModel.getUserDetails()
        initBackButton()
        return viewBinding.root
    }

    private fun initViewBinding() {
        viewBinding = FragmentUserDetailsBinding.inflate(layoutInflater)
    }

    private fun openChangePasswordLayout(){
        viewBinding.changePasswordBtn.setOnClickListener {
            it.makeGone()
            viewBinding.layoutChangePassword.root.makeVisible()
        }
    }

    private fun initBackButton(){
        viewBinding.fabBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun collectUserDetailsData() {
        lifecycleScope.launch {
            viewModel.response.collect {
                when (it) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            viewBinding.progressBar.makeGone()
                            setAllInfo(it.data)
                        }
                    }
                    is Resource.Progress -> {
                        viewBinding.progressBar.makeVisible()
                    }
                    is Resource.Error -> {
                        Timber.d("Error: ${it.errorData.error_message}")
                    }
                }
            }
        }
    }

    private fun collectChangePasswordResponse() {
        lifecycleScope.launch {
            viewModel.responseChangePassword.collect {
                when (it) {
                    is Resource.Success -> {
                            with(viewBinding.layoutSuccess){
                                viewBinding.layoutChangePassword.layoutError.root.makeGone()
                                root.makeVisible()
                                tvState.text = "Success"
                                tvStateDescription.text = "Your password has been changed"
                            }
                    }
                    is Resource.Error-> {
                        viewBinding.layoutSuccess.root.makeGone()
                        with(viewBinding.layoutChangePassword.layoutError){
                            root.makeVisible()
                            tvState.text = "Error"
                            tvStateDescription.text = "Please, check provided data again"
                        }
                        viewBinding.layoutChangePassword.tilOldPassword.error = "Incorrect password"
                    }
                }
            }
        }
    }

    private fun activateConfirmationButtonOnFocus() {
        with(viewBinding.layoutChangePassword) {
            tieOldPassword.doAfterTextChanged {
                tilOldPassword.error = null
                tilNewPassword.error = null
            }
            tieNewPassword.doAfterTextChanged {
                tilOldPassword.error = null
                tilNewPassword.error = null
            }
        }
    }

    private fun setAllInfo(userDetails: UserDetails) {
        with(viewBinding) {
            titleTv.makeVisible()
            changePasswordBtn.makeVisible()
            nameTv.text = userDetails.name + " " + userDetails.surname
            loginTv.text = "(" + userDetails.login + ")"
            emailTv.text = userDetails.email
        }
    }
    private fun setConfirmationButton() =
        viewBinding.layoutChangePassword.changePasswordBtn.setOnClickListener {
            if (checkProvidedContent())
                changePassword(viewBinding.layoutChangePassword.tieOldPassword.text.toString(),
                    viewBinding.layoutChangePassword.tieNewPassword.text.toString())
        }

    private fun changePassword(oldPassword: String, newPassword: String) {
        viewModel.changePassword(oldPassword, newPassword)
    }


    private fun checkProvidedContent(): Boolean =
        with(viewBinding.layoutChangePassword) {
            tilNewPassword.passwordValidation(tieNewPassword.text.toString())
        }
}