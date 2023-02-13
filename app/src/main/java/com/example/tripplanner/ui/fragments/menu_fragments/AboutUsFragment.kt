package com.example.tripplanner.ui.fragments.menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentSettingsAboutUsBinding
import com.example.tripplanner.ui.activities.MenuActivity

class AboutUsFragment : Fragment() {

    private lateinit var viewBinding: FragmentSettingsAboutUsBinding
    private val menuActivityInstance by lazy { activity as MenuActivity }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewBinding()
         viewBinding.fabBack.setOnClickListener {
             menuActivityInstance.onBackPressed()
         }
        return viewBinding.root
    }

    private fun initViewBinding(){
        viewBinding = FragmentSettingsAboutUsBinding.inflate(layoutInflater)
    }
}