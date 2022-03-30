package com.example.myshop.domain.repository

import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter

interface ProductsRepository {

   suspend fun addProducts(products: Products)

   suspend fun addProductsInCart(products: ProductsInCart, constants: String)

   suspend fun addProductInOrders(productsInOrder: ProductsInOrder)

   suspend fun deleteProduct(constants: String)

   suspend fun deleteImageProduct(fileExtension: String)

   suspend fun deleteAddress(userId: String)

   suspend fun deleteProductsInCart(userId: String)

   suspend fun getProduct(userId: String, constants: String): ArrayList<Products>

   suspend fun getAllProducts(): ArrayList<Products>

   suspend fun getProductInCart(userId: String): ArrayList<ProductsInCart>

   suspend fun getProductInOrders(userId: String): ArrayList<ProductsInOrder>

  suspend  fun getAllPrice(userId: String): Float?

}