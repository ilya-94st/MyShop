package com.example.myshop.domain.repository

import com.example.myshop.domain.models.Products
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment

interface ProductsRepository {

    fun addProducts(fragment: AddProductsFragment, products: Products)

    fun deleteProduct(productsFragment: ProductsFragment)

    fun deleteImageProduct(productsFragment: ProductsFragment)

    fun getProduct(productsAdapter: ProductsAdapter, userId: String)

    fun getAllProducts(allProductsAdapter: AllProductsAdapter)

}