package com.example.tripplanner.views.fragments.menufragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tripplanner.databinding.FragmentTemporaryClosedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryTripsFragment : Fragment() {

    private lateinit var binding: FragmentTemporaryClosedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        return binding.root
    }

    private fun initBinding() {
        binding = FragmentTemporaryClosedBinding.inflate(layoutInflater)
    }
}