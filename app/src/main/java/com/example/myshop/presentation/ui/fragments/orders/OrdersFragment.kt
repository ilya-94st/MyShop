package com.example.myshop.presentation.ui.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentOrdersBinding
import com.example.myshop.presentation.base.BaseFragment


class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrdersBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_settingsFragment)
        }
    }
}