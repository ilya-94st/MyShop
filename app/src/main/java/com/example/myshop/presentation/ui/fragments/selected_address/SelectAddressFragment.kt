package com.example.myshop.presentation.ui.fragments.selected_address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentSelectAddressBinding
import com.example.myshop.presentation.adapters.AddressAdapter
import com.example.myshop.presentation.base.BaseFragment
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
        initAdapter()

    }

    private fun initAdapter() {
        addressAdapter = AddressAdapter()
        binding.rvAddress.adapter = addressAdapter

        viewModel.user.observe(viewLifecycleOwner){
            viewModel.getItemsAddressUser(it.id)
        }
        viewModel.addressUser.observe(viewLifecycleOwner){
            addressAdapter.submitList(it)

        }
        addressAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("userAdres", it)
            }
            findNavController().navigate(R.id.action_selectAddressFragment_to_checkoutOrderFragment, bundle)
        }
    }
}