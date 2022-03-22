package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.ProductsAdapter
import javax.inject.Inject

class GetProducts @Inject constructor(private val productsRepository: ProductsRepository) {

    operator fun invoke(productsAdapter: ProductsAdapter, userId: String) {
        productsRepository.getProduct(productsAdapter, userId)
    }
}