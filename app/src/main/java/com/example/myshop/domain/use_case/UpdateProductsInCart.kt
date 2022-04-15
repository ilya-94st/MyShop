package com.example.myshop.domain.use_case

import android.util.Log
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateProductsInCart @Inject constructor(private val updateRepository: UpdateRepository) {

  suspend operator fun invoke(oldProductsInCart: ProductsInCart, quantity: Int) {
        try {
        val productHashMap = mutableMapOf<String, Any>()

        if (oldProductsInCart.quantity != quantity) {
            productHashMap[Constants.QUANTITIES_IN_PRODUCTS_IN_CART] = quantity
        }

        updateRepository.upDataProductsInCart(productHashMap, oldProductsInCart)
        } catch (e: Exception) {
            Log.e("updateProductsInCart", "$e")
        }
    }
}