package com.example.trip_planner_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trip_planner_application.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initBinding(inflater, container)
        return binding.root

    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
    }


}