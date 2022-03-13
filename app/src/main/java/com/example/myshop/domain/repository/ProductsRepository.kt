package com.example.myshop.domain.repository

import com.example.myshop.domain.models.Products
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment

interface ProductsRepository {

   suspend fun addProducts(products: Products)

   suspend fun deleteProduct()

   suspend fun deleteImageProduct()

    fun getProduct(productsAdapter: ProductsAdapter, userId: String)

    fun getAllProducts(allProductsAdapter: AllProductsAdapter)

}