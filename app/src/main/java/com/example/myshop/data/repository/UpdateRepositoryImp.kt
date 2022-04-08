package com.example.myshop.data.repository

import com.example.myshop.common.EventClass
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateRepositoryImp @Inject constructor(): UpdateRepository {

    override fun updateUserProfileData(userHashMap: HashMap<String, Any>): EventClass? {
        var registerResult: EventClass? = null

        try {
            registerResult = EventClass.Success
            FireStore().updateUserProfileData(userHashMap)
        } catch (e: Exception){
        registerResult =  EventClass.ErrorIn("${e.message}")
        }
    return registerResult
    }

    override  fun upDataProducts(products: Map<String, Any>, oldProducts: Products) {
          FireStore().updateProductsData(products, oldProducts)
    }

    override suspend fun upDataProductsInCart(
        products: Map<String, Any>,
        oldProductsInCart: ProductsInCart
    ) {
        FireStore().updateProductsInCart(products, oldProductsInCart)
    }
}