package com.example.myshop.presentation.ui.fragments.checkout_orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.databinding.FragmentCheckoutOrderBinding
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutOrderFragment : BaseFragment<FragmentCheckoutOrderBinding>() {
    private val savArgs: CheckoutOrderFragmentArgs by navArgs()
    private val viewModel: CheckoutOrderViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCheckoutOrderBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getItemsOrder()
        initAdapter()
        observerProductsInCart()
    }

    @SuppressLint("SetTextI18n")
    private fun getItemsOrder() {
        val address = savArgs.userAdres
        binding.tvMyNote.text = address.notes
        binding.tvPhoneNumber.text = "${address.phoneNumber}"
        binding.tvMyAddressZip.text = address.address + "," + address.zipCode
        binding.tvFullName.text = address.name
        binding.tvNameAddress.text = address.chooseAddress
        viewModel.users.observe(viewLifecycleOwner){
            viewModel.getAllPrice(it.id)
        }
        viewModel.allPrice.observe(viewLifecycleOwner){
            binding.tvSubtotal.text = "Subtotal $it"
            binding.tvShippingCharge.text = "Shipping Charge 10$"
            binding.tvTotalAmount.text = "Total Amount ${it + 10F}"
        }
    }

    private fun initAdapter() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.adapter = productsAdapter
    }

    private fun observerProductsInCart() {
        viewModel.users.observe(viewLifecycleOwner){
            viewModel.getProductInCart(productsAdapter, it.id)
        }
    }
}