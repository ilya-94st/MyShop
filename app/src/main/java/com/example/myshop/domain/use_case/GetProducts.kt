package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.CheckProductsRepository
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.ProductsFragment
import javax.inject.Inject

class GetProducts @Inject constructor(private val productsRepository: ProductsRepository, private val checkProductsRepository: CheckProductsRepository) {

    fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        productsRepository.getProduct(productsAdapter, userId)
    }

    fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        productsRepository.getAllProducts(allProductsAdapter)
    }

    fun checkUserDetails(productsFragment: ProductsFragment) {
        checkProductsRepository.checkUsersGetProducts(productsFragment)
    }
}