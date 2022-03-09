package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.data.FireStore
import com.example.myshop.databinding.FragmentProductsBinding
import com.example.myshop.domain.use_case.GetProducts
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment


class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
private lateinit var productsAdapter: ProductsAdapter
private lateinit var getProducts: GetProducts




    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibAddProducts.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductsFragment)
        }
        getProducts = GetProducts()
        initAdapter()

        productsAdapter.setOnItemClickListener {
           FireStore().deleteProducts(this)
       //    FireStore().deleteImage(this)
        }
    }

    private fun initAdapter() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.adapter = productsAdapter
         getProducts.getProduct(productsAdapter)

    }
}