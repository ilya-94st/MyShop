package com.example.myshop.domain.use_case

import com.example.myshop.data.repository.ShopRepositoryImp
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class GetProducts(private val shopRepositoryImp: ShopRepositoryImp) {

    fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        shopRepositoryImp.getProduct(productsAdapter, userId)
    }

    fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
       shopRepositoryImp.getAllProducts(allProductsAdapter)
    }

    fun checkUserDetails(productsFragment: ProductsFragment) {
        shopRepositoryImp.checkUsersGetProducts(productsFragment)
    }
}