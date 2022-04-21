package com.example.myshop.presentation.ui

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myshop.R
import com.example.myshop.common.NetworkFound
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.presentation.tools.toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NetworkFound.ConnectivityReceiverListener {
    private lateinit var binding: ActivityMainBinding
    private var snackBar: Snackbar? = null
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createMenu()
        registerReceiver(NetworkFound(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
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

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        NetworkFound.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        NetworkFound.connectivityReceiverListener = null
    }

    @SuppressLint("ShowToast")
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackBar = Snackbar.make(binding.root, "You are offline", Snackbar.LENGTH_LONG).setAction("dismiss"){
                snackBar?.dismiss()
            }
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }
    }
   // override fun onBackPressed() {
   //     super.onBackPressed()
   //     doubleBackToExit()
   // }
