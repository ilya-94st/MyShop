package com.example.myshop.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentMyCartBinding
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.MyCartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCartFragment : BaseFragment<FragmentMyCartBinding>() {
    private val viewModel: MyCartViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMyCartBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        binding.btCheckout.setOnClickListener {
           findNavController().navigate(R.id.action_myCartFragment_to_selectAddressFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initAdapter() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.adapter = productsAdapter
        viewModel.users.observe(viewLifecycleOwner){
            viewModel.getProductInCart(productsAdapter, it.id)
            viewModel.getAllPrice(it.id)
        }
        viewModel.allPrice.observe(viewLifecycleOwner){
            binding.tvSubPrice.text = "$it"
            binding.tvShippingPrice.text = "${10}"
            binding.tvTotalSum.text = "${it + 10}"
        }
    }
}