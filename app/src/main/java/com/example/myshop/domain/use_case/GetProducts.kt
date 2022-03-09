package com.example.myshop.domain.use_case

import com.example.myshop.data.FireStore
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter

class GetProducts {

    fun getProduct(productsAdapter: ProductsAdapter) {
        FireStore().getProducts(productsAdapter)
    }

    fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        FireStore().getAllProducts(allProductsAdapter)
    }
}