package com.example.tripplanner.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ActivityMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(binding.root)
        setupNavigationBottomBar()
    }

    override fun onBackPressed() {}

    private fun initBinding(){
        binding = ActivityMenuBinding.inflate(layoutInflater)
    }

    private fun setupNavigationBottomBar(){
        val navigationController = findNavController(R.id.fragment)
        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.setupWithNavController(navigationController)
    }
}