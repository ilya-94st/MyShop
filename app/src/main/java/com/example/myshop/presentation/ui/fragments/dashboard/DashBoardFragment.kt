package com.example.myshop.presentation.ui.fragments.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.common.EventClass
import com.example.myshop.databinding.FragmentDashBoardBinding
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDashBoardBinding::inflate

    private lateinit var allProductsAdapter: AllProductsAdapter
    private val viewModel: DashBoardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_settingsFragment)
        }
        initAdapter()
        observeProducts()

        allProductsAdapter.setOnItemClickListener {
             val bundle = Bundle().apply {
                 putSerializable("products", it)
             }
            findNavController().navigate(R.id.action_dashBoardFragment_to_descriptionProductFragment, bundle)
        }
    }

    private fun observeProducts() {
        showProgressDialog("Please wait...")
        viewModel.getAllProducts()
        viewModel.result.observe(viewLifecycleOwner) {
                event ->
            when (event) {
                is EventClass.GetAllProducts -> {
                    allProductsAdapter.submitList(event.list)
                    hideProgressDialog()
                }
                is EventClass.ErrorIn -> {
                    hideProgressDialog()
                    errorSnackBar(event.error, true)
                }
                else -> Unit
            }
        }
    }

    private fun initAdapter() {
        allProductsAdapter = AllProductsAdapter()
        binding.rvProducts.adapter = allProductsAdapter
    }
}