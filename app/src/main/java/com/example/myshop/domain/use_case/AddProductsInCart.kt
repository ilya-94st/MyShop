package com.example.myshop.domain.use_case

import com.example.myshop.common.Constants
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class AddProductsInCart @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(product: ProductsInCart) {
        productsRepository.addProductsInCart(product, Constants.PRODUCT_IN_CART)
    }
}