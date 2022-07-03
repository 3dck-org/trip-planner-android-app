package com.example.trip_planner_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trip_planner_application.databinding.FragmentSigninBinding

/**
 * @author Pavel Zlacheuski
 * Fragment for signing into the application
 */
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View{
        initBinding(inflater, container)

        return binding.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
    }

}