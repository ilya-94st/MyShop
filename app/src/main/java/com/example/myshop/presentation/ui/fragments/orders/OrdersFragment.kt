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
import com.example.myshop.presentation.ui.prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    private lateinit var ordersAdapter: OrdersAdapter
    private val viewModel: OrdersViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrdersBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                initAdapter()
    }

    private fun initAdapter() {
        ordersAdapter = OrdersAdapter()
        binding.rvProducts.adapter = ordersAdapter

        viewModel.getOrders(prefs.idUser)

        viewModel.order.observe(viewLifecycleOwner){ order->
            ordersAdapter.submitList(order)
            ordersAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                   putSerializable("order", it)
                }
                findNavController().navigate(R.id.action_ordersFragment_to_ordersDetailsFragment, bundle)
            }
        }
    }
}