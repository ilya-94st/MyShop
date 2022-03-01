package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentSplashBinding
import com.example.myshop.presentation.base.BaseFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSplashBinding::inflate

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        //       requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        //   } else {
        //       requireActivity().window.setFlags(
        //            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //             WindowManager.LayoutParams.FLAG_FULLSCREEN
        //      )
        //   }

        GlobalScope.launch {
            delay(2000)
            activity?.runOnUiThread {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }
}