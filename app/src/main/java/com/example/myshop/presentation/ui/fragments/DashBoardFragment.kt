package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentDashBoardBinding
import com.example.myshop.presentation.base.BaseFragment


class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDashBoardBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_settingsFragment)
        }
    }
}