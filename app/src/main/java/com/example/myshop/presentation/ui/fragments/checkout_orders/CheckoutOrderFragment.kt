package com.example.myshop.presentation.ui.fragments.checkout_orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCheckoutOrderBinding
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.presentation.adapters.ProductsAddInOrder
import com.example.myshop.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutOrderFragment : BaseFragment<FragmentCheckoutOrderBinding>() {
    private val savArgs: CheckoutOrderFragmentArgs by navArgs()
    private val viewModel: CheckoutOrderViewModel by viewModels()
    private lateinit var productsAddInOrder: ProductsAddInOrder
    private var time = ""

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCheckoutOrderBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.time.observe(viewLifecycleOwner){
            time = it
        }
        initAdapter()
        getItemsOrder()

        binding.btPlaceOder.setOnClickListener {
            addProductsInOrder()
            delete()
            hideProgressDialog()
            findNavController().navigate(R.id.action_checkoutOrderFragment_to_dashBoardFragment)
            toast("You order was placed successfully")
        }
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

    private fun delete() {
        viewModel.users.observe(viewLifecycleOwner){
            val userId = it.id
            viewModel.deleteProducts(userId)
            viewModel.deleteAddress(userId)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initAdapter() {
        productsAddInOrder = ProductsAddInOrder()
        binding.vpProducts.adapter = productsAddInOrder
        viewModel.users.observe(viewLifecycleOwner){
            viewModel.getProductInCart(it.id)
        }
        viewModel.products.observe(viewLifecycleOwner){ products->
            productsAddInOrder.submitList(products)
        }
        TabLayoutMediator(binding.table, binding.vpProducts) {
                _, _ ->
        }.attach()
    }

    private fun addProductsInOrder() {
        showProgressDialog("Please wait ...")
        viewModel.products.observe(viewLifecycleOwner){ products->
            products.forEach {
                val addressItems = savArgs.userAdres
                val notes = addressItems.notes
                val phoneNumber = "${addressItems.phoneNumber}"
                val nameAddress = addressItems.address
                val addressZip = addressItems.zipCode
                val fullName= addressItems.name
                val chooseAddress = addressItems.chooseAddress
                viewModel.users.observe(viewLifecycleOwner){ userId->
                    val productInOrder = ProductsInOrder(userId.id,
                        it.idOrder,
                        it.title,
                        it.price,
                        it.image,
                        it.currency,
                        fullName,
                        nameAddress,
                        phoneNumber.toLong(),
                        addressZip,
                        notes,
                        chooseAddress,
                        time
                    )
                    viewModel.addProductInOrder(productInOrder)
                }
            }
        }
    }


}