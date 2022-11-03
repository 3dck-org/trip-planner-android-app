package com.example.tripplanner.views.fragments.menufragments

import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.tripplanner.R
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.databinding.FragmentSettingsBinding
import com.example.tripplanner.sharedpreferences.EncryptedSharedPreferences
import com.example.tripplanner.views.activities.SplashActivity
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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
        handleMotionLayoutTransition()
        handleSettingsClick()
        return binding.root
    }

    private fun handleSettingsClick(){
        binding.logoutSetting.findViewById<MaterialButton>(R.id.setting_mb).setOnClickListener { logoutSettingClick() }
    }

    private fun handleMotionLayoutTransition() {
        binding.themeMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when (currentId) {
                    R.id.start -> {
                        //setTheme(false)
                    }
                    R.id.end -> {
                        //setTheme(true)
                    }
                    else -> {
                        //setTheme(false)
                    }
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

    private fun setTheme(isNightMode: Boolean) {
        when (isNightMode) {
            true -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
    }

    private fun initBinding() {
        binding = FragmentSettingsBinding.inflate(LayoutInflater.from(this.context))
    }

    private fun logoutSettingClick() {
        sharedPref.apply {
            remove(Constants.TOKEN)
            remove(Constants.REFRESH_TOKEN)
        }
        Timber.d("******")
        startActivity(Intent(activity, SplashActivity::class.java))
    }
}