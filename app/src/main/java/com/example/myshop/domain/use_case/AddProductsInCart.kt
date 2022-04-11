package com.example.myshop.domain.use_case

import android.util.Log
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.repository.ProductsRepository
import java.io.IOException
import javax.inject.Inject

class AddProductsInCart @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(product: ProductsInCart) {
        try {
        productsRepository.addProductsInCart(product, Constants.PRODUCT_IN_CART)
    } catch (e: IOException) {
        Log.e("addProductsInCart", "error addProductsInCart")
    }
    }
}