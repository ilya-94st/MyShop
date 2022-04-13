package com.example.myshop.presentation.ui.fragments.selected_address

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
    private var userId = ""
    private var idAddress = 0L

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSelectAddressBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_selectAddressFragment_to_addAddressFragment)
        }
        initAdapter()
        deleteAddressSwipe()
    }

    private fun initAdapter() {
        viewModel.user.observe(viewLifecycleOwner){
            viewModel.getItemsAddressUser(it.id)
            userId = it.id
        }
        viewModel.addressUser.observe(viewLifecycleOwner){ address ->
            addressAdapter = AddressAdapter(address)
            binding.rvAddress.adapter = addressAdapter
            address.forEach {
                idAddress = it.idAddress
            }
            addressAdapter.setOnItemClickListenerDeleteItem {
                viewModel.deleteAddress(idAddress)
            }
            addressAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("userAdres", it)
                }
                findNavController().navigate(R.id.action_selectAddressFragment_to_checkoutOrderFragment, bundle)
            }
        }
    }

    private fun alertDialogDeleteProduct(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                viewModel.deleteAddress(idAddress)
            }
            .setNegativeButton(
                "No"
            ) {
                    dialog, _ ->
                dialog.cancel()
                initAdapter()
            }
        builder.create().show()
    }

    private fun deleteAddressSwipe() {
        val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
            , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            @SuppressLint("ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                alertDialogDeleteProduct("Do you want a delete product?")
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvAddress)
        }
    }
}