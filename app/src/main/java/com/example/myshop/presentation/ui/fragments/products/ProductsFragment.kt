package com.example.myshop.presentation.ui.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentProductsBinding
import com.example.myshop.domain.models.Users
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
private lateinit var productsAdapter: ProductsAdapter
private lateinit var mUserDetails: Users
private var userId = ""
    private val viewModel: ProductViewModel by viewModels()



    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibAddProducts.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductsFragment)
        }

        binding.ibCart.setOnClickListener {
           findNavController().navigate(R.id.action_productsFragment_to_myCartFragment)
        }

        initAdapter()



        productsAdapter.setOnItemClickListener {
           viewModel.deleteProduct(userId)
           viewModel.deleteImage(userId)
        }
    }


    private fun initAdapter() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.adapter = productsAdapter
        viewModel.users.observe(viewLifecycleOwner){
            mUserDetails = it
            userId =  mUserDetails.id
            viewModel.getProduct(userId)
        }
        viewModel.products.observe(viewLifecycleOwner){
            productsAdapter.submitList(it)
        }
    }
}