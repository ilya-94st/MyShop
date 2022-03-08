package com.example.myshop.domain.use_case

import com.example.myshop.data.FireStore
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class GetProducts {

    fun getProduct(productsAdapter: ProductsAdapter, fragment: ProductsFragment) {
        FireStore().getProducts(fragment, productsAdapter)
    }

    fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        FireStore().getAllProducts(allProductsAdapter)
    }
}