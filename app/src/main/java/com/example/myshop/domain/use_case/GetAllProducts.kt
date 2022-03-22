package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
import javax.inject.Inject

class GetAllProducts @Inject constructor(private val productsRepository: ProductsRepository) {

  operator fun invoke(allProductsAdapter: AllProductsAdapter) {
        productsRepository.getAllProducts(allProductsAdapter)
    }
}