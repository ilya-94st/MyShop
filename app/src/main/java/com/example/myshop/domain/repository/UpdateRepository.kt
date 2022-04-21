package com.example.myshop.domain.repository

import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products


interface UpdateRepository {

    fun updateUserProfileData(userHashMap: HashMap<String, Any>): EventClass?

   fun upDataProducts(products: Map<String, Any>, oldProducts: Products)

  suspend fun upDataProductsInCart(products: Map<String, Any>, oldQuantity: Int, idProduct: Long)
}