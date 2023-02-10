package com.example.tripplanner.ui.fragments.menu_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tripplanner.R
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.databinding.FragmentSettingsBinding
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.utils.sharedpreferences.EncryptedSharedPreferences
import com.example.tripplanner.ui.activities.SplashActivity
import com.example.tripplanner.ui.activities.UserActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences
    private lateinit var binding: FragmentSettingsBinding
    private val menuActivityInstance by lazy { activity as MenuActivity }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        menuActivityInstance.showMenu()
        handleSettingsClick()
        return binding.root
    }

    private fun handleSettingsClick(){
        binding.logoutSetting.optionViewBinding.optionMaterialButton.setOnClickListener { logoutSettingClick() }
        binding.accontDetailsSetting.optionViewBinding.optionMaterialButton.setOnClickListener { accountDetailsSettingClick() }
    }

    private fun initBinding() {
        binding = FragmentSettingsBinding.inflate(LayoutInflater.from(this.context))
    }

    private fun logoutSettingClick() {
        sharedPref.apply {
            remove(Constants.TOKEN)
            remove(Constants.TOKEN)
            remove(Constants.REFRESH_TOKEN)
        }
        startActivity(Intent(activity, SplashActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    private fun accountDetailsSettingClick(){
        findNavController().navigate(R.id.userDetailsFragment)
    }
}