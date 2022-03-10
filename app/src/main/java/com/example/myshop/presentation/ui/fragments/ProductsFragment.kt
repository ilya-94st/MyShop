package com.example.myshop.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentProductsBinding
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.DeleteProducts
import com.example.myshop.domain.use_case.GetProducts
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment


class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
private lateinit var productsAdapter: ProductsAdapter
private lateinit var getProducts: GetProducts
private lateinit var mUserDetails: Users
private var userId = ""
    private lateinit var deleteProducts: DeleteProducts



    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                 deleteProducts = DeleteProducts()
        binding.ibAddProducts.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductsFragment)
        }
        getProducts = GetProducts()
        initAdapter()

        productsAdapter.setOnItemClickListener {
           deleteProducts.deleteProduct(this)
           deleteProducts.deleteImage(this)
        }
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    @SuppressLint("SetTextI18n")
    fun userDetailsSuccessful(users: Users) {
        mUserDetails = users
        userId =  users.id
        getProducts.getProduct(productsAdapter, userId)
    }

    private fun getUserDetails() {
        getProducts.checkUserDetails(this)
    }

    private fun initAdapter() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.adapter = productsAdapter
    }
}