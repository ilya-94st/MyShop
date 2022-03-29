package com.example.myshop.presentation.ui.fragments.orders_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.myshop.databinding.FragmentOrdersDetailsBinding
import com.example.myshop.presentation.adapters.OrderDetailsAdapter
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersDetailsFragment : BaseFragment<FragmentOrdersDetailsBinding>() {
    private val viewModel: OrdersDetailsViewModel by viewModels()
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrdersDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }


    @SuppressLint("SetTextI18n")
    private fun initAdapter() {
        orderDetailsAdapter = OrderDetailsAdapter()
        binding.rvProducts.adapter = orderDetailsAdapter
        viewModel.user.observe(viewLifecycleOwner){
            viewModel.getItemsAddressUser(it.id)
            viewModel.getOrders(orderDetailsAdapter, it.id)
        }

        viewModel.addressUser.observe(viewLifecycleOwner){
            it.forEach {
                address->
                binding.tvMyNote.text = address.notes
                binding.tvPhoneNumber.text = "${address.phoneNumber}"
                binding.tvMyAddressZip.text = address.address + "," + address.zipCode
                binding.tvFullName.text = address.name
                binding.tvNameAddress.text = address.chooseAddress
            }
        }
        viewModel.user.observe(viewLifecycleOwner){
            viewModel.getAllPrice(it.id)
        }
        viewModel.allPrice.observe(viewLifecycleOwner){
            binding.tvSubtotal.text = "Subtotal $it"
            binding.tvShippingCharge.text = "Shipping Charge 10$"
            binding.tvTotalAmount.text = "Total Amount ${it + 10F}"
        }
    }


}