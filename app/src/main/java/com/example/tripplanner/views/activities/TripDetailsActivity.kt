package com.example.tripplanner.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tripplanner.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)
    }
}