package com.example.myshop.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myshop.R
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.presentation.tools.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createMenu()
    }

    private fun createMenu() {
        val navHostFragment =supportFragmentManager.findFragmentById((R.id.fragmentContainerView)) as NavHostFragment
        binding.btNavigation.setupWithNavController(navHostFragment.findNavController())
        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.dashBoardFragment, R.id.productsFragment, R.id.ordersFragment ->
                        binding.btNavigation.visibility = View.VISIBLE
                    else -> binding.btNavigation.visibility = View.GONE
                }
            }
    }

    fun doubleBackToExit() {
        if(doubleBackToExitPressedOnce) {
            super.onBackPressed()
        }

        this.doubleBackToExitPressedOnce = true

        toast("please click back again to exit")

        Handler().postDelayed({doubleBackToExitPressedOnce = false}, 2000)
    }

   // override fun onBackPressed() {
   //     super.onBackPressed()
   //     doubleBackToExit()
   // }
}