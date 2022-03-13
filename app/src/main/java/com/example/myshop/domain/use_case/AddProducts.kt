package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject


class AddProducts @Inject constructor(private val productsRepository: ProductsRepository) {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed)

   suspend fun addProducts(products: Products) {
        productsRepository.addProducts(products)
    }
}