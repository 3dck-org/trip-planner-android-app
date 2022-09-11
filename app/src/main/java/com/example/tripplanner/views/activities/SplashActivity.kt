package com.example.tripplanner.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.databinding.ActivitySplashBinding
import com.example.tripplanner.repositories.BaseRepository
import com.example.tripplanner.sharedpreferences.EncryptedSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(binding.root)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        checkAutoLogin()
    }

    private fun initBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
    }

    private fun checkAutoLogin() {
        val token = sharedPref.getPreference(Constants.TOKEN) ?: ""
        if (token.isNotEmpty()) {
            BaseRepository.addToken(token)
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }
    }

}