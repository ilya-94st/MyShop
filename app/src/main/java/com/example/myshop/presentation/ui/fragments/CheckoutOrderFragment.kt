package com.example.myshop.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.databinding.FragmentCheckoutOrderBinding
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.CheckoutOrderViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutOrderFragment : BaseFragment<FragmentCheckoutOrderBinding>() {
    private val savArgs: CheckoutOrderFragmentArgs by navArgs()
    private val viewModel: CheckoutOrderViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCheckoutOrderBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getItemsOrder()
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
            binding.tvSubtotal.text = "$it"
            binding.tvTotalAmount.text = "${it + 10F}"
        }
    }
}