package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import javax.inject.Inject

class GetProducts @Inject constructor(private val productsRepository: ProductsRepository) {

    fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        productsRepository.getProduct(productsAdapter, userId)
    }

    fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        productsRepository.getAllProducts(allProductsAdapter)
    }
}