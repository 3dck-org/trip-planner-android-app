package com.example.tripplanner.views.fragments.menufragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.databinding.FragmentSettingsBinding
import com.example.tripplanner.sharedpreferences.EncryptedSharedPreferences
import com.example.tripplanner.views.activities.SplashActivity
import com.example.tripplanner.views.activities.UserActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
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
            remove(Constants.REFRESH_TOKEN)
        }
        startActivity(Intent(activity, SplashActivity::class.java))
    }

    private fun accountDetailsSettingClick(){
        startActivity(Intent(activity, UserActivity::class.java))
    }
}