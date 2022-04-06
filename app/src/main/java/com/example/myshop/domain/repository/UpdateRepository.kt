package com.example.myshop.domain.repository

import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products


interface UpdateRepository {

    fun updateUserProfileData(userHashMap: HashMap<String, Any>): EventClass?

  suspend fun upDataProducts(products: HashMap<String, Any>, oldProducts: Products)
}