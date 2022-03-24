package com.example.myshop.domain.repository

import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter

interface ProductsRepository {

   suspend fun addProducts(products: Products)

   suspend fun addProductsInCart(products: ProductsInCart, constants: String)

   suspend fun deleteProduct()

   suspend fun deleteImageProduct(fileExtension: String)

    fun getProduct(productsAdapter: ProductsAdapter, userId: String, constants: String)

    fun getAllProducts(allProductsAdapter: AllProductsAdapter)

    fun getProductInCart(productsAdapter: ProductsAdapter, userId: String)

  suspend  fun getAllPrice(userId: String): Float?
}