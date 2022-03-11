package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.CheckProductsRepository
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.ui.fragments.AddProductsFragment


class AddProducts(private val productsRepository: ProductsRepository, private val checkProductsRepository: CheckProductsRepository) {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed)

    fun addProducts(fragment: AddProductsFragment, products: Products) {
        productsRepository.addProducts(fragment, products)
    }

    fun checkUserDetails(addProductsFragment: AddProductsFragment) {
        checkProductsRepository.checkUserDetailsAddProducts(addProductsFragment)
    }
}