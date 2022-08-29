package com.example.tripplanner.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tripplanner.databinding.ActivityStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(binding.root)
    }

    private fun initBinding() {
        binding = ActivityStartBinding.inflate(layoutInflater)
    }

}