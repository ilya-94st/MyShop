package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import javax.inject.Inject


class AddProducts @Inject constructor(private val productsRepository: ProductsRepository) {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed)

    fun addProducts(fragment: AddProductsFragment, products: Products) {
        productsRepository.addProducts(fragment, products)
    }
}