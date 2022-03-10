package com.example.myshop.domain.use_case

import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.Users
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class GetProducts {

    fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        FireStore().getProducts(productsAdapter, userId)
    }

    fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        FireStore().getAllProducts(allProductsAdapter)
    }

    fun checkUserDetails(productsFragment: ProductsFragment) {
        FireStore().getUsersDetails(productsFragment)
    }
}