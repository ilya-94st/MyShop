package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.data.repository.ShopRepositoryImp
import com.example.myshop.domain.models.Products
import com.example.myshop.presentation.ui.fragments.AddProductsFragment


class AddProducts(private val shopRepositoryImp: ShopRepositoryImp) {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed)

    fun addProducts(fragment: AddProductsFragment, products: Products) {
        shopRepositoryImp.addProducts(fragment, products)
    }

    fun checkUserDetails(addProductsFragment: AddProductsFragment) {
       shopRepositoryImp.checkUserDetailsAddProducts(addProductsFragment)
    }
}