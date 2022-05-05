package com.example.myshop.presentation.ui.fragments.selected_address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.common.EventClass
import com.example.myshop.databinding.FragmentSelectAddressBinding
import com.example.myshop.presentation.adapters.AddressAdapter
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.ui.prefs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectAddressFragment : BaseFragment<FragmentSelectAddressBinding>() {
    private val viewModel: SelectAddressViewModel by viewModels()
    private lateinit var addressAdapter: AddressAdapter

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSelectAddressBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_selectAddressFragment_to_addAddressFragment)
        }
       observeAddress()
    }

    private fun observeAddress() {
        viewModel.getItemsAddressUser(prefs.idUser)


        viewModel.result.observe(viewLifecycleOwner){ event ->
            when (event) {
                is EventClass.GetAddressItems -> {
                    addressAdapter = AddressAdapter(event.list)
                    binding.rvAddress.adapter = addressAdapter

                    addressAdapter.setOnItemClickListenerDeleteItem {
                        viewModel.deleteAddress(it.idAddress)
                    }
                    addressAdapter.setOnItemClickListener {
                        val bundle = Bundle().apply {
                            putSerializable("userAdres", it)
                        }
                        findNavController().navigate(R.id.action_selectAddressFragment_to_checkoutOrderFragment, bundle)
                    }
                }
                is EventClass.ErrorIn -> {
                    errorSnackBar(event.error, true)
                }
                else -> Unit
            }

        }
    }
}