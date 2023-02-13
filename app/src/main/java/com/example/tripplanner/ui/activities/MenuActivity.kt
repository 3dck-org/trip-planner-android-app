package com.example.tripplanner.ui.activities

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ActivityMenuBinding
import com.example.tripplanner.extensions.hide
import com.example.tripplanner.extensions.show
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigationBottomBar()
    }
    
    private fun initBinding() {
        binding = ActivityMenuBinding.inflate(layoutInflater)
    }

    private fun setupNavigationBottomBar() {
        val navigationController = findNavController(R.id.fragment)
        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.setupWithNavController(navigationController)
    }

    fun hideMenu(){
        binding.bottomNavigation.hide()
    }

    override fun onBackPressed() {
        binding.appToolbar.setExpanded(true,true)
        super.onBackPressed()
    }

    fun expandToolbar(){
        binding.appToolbar.setExpanded(true,false)
    }

    fun expandBottomNavigationBar(){
        val layoutParams = binding.bottomNavigation.layoutParams as CoordinatorLayout.LayoutParams
        val bottomViewNavigationBehavior = layoutParams.behavior as HideBottomViewOnScrollBehavior
        bottomViewNavigationBehavior.slideUp(binding.bottomNavigation)
    }

    fun bottonNavigationBar() = binding.bottomNavigation

    fun showMenu() {
        binding.bottomNavigation.show()
    }
}