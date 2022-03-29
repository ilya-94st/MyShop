package com.example.myshop.presentation.ui.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentOrdersBinding
import com.example.myshop.presentation.adapters.OrdersAdapter
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    private lateinit var ordersAdapter: OrdersAdapter
    private val viewModel: OrdersViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrdersBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIdProductInCart()
        viewModel.response.observe(viewLifecycleOwner){
            if (it == true) {
                initAdapter()
                getOrders()
            }
        }
    }

    private fun initAdapter() {
        ordersAdapter = OrdersAdapter()
        binding.rvProducts.adapter = ordersAdapter
        ordersAdapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_ordersFragment_to_ordersDetailsFragment)
        }
    }

    private fun getOrders() {
        viewModel.user.observe(viewLifecycleOwner){
            viewModel.getOrders(ordersAdapter, it.id)
        }
    }
}