package com.example.myshop.domain.use_case

import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class AddProductInOrder @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(productsInOrder: ProductsInOrder) {
        productsRepository.addProductInOrders(productsInOrder)
    }
}