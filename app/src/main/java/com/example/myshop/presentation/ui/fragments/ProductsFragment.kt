package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentProductsBinding
import com.example.myshop.presentation.base.BaseFragment


class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_settingsFragment)
        }
    }
}