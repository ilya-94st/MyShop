package com.example.myshop.domain.use_case

import android.util.Log
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.repository.ProductsRepository
import java.io.IOException
import javax.inject.Inject

class AddProductInOrder @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(productsInOrder: ProductsInOrder) {
        try {
        productsRepository.addProductInOrders(productsInOrder)
    } catch (e: IOException) {
        Log.e("addProductInOrders", "error addProductsInCart")
    }
    }
}