package com.example.tripplanner.ui.fragments.start_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        onButtonClick()
        return binding.root
    }

    private fun initBinding(){
        binding = FragmentStartBinding.inflate(layoutInflater)
    }

    private fun onButtonClick(){
        with(binding){
            signInBtn.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }
            signUpBtn.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registerFragment)
            }
        }
    }

}