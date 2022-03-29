package com.example.myshop.domain.repository

import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.OrderDetailsAdapter
import com.example.myshop.presentation.adapters.OrdersAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter

interface ProductsRepository {

   suspend fun addProducts(products: Products)

   suspend fun addProductsInCart(products: ProductsInCart, constants: String)

   suspend fun addProductInOrders(productsInOrder: ProductsInOrder)

   suspend fun deleteProduct(constants: String)

   suspend fun deleteImageProduct(fileExtension: String)

   suspend fun deleteProductsInCart(userId: String)

    fun getProduct(productsAdapter: ProductsAdapter, userId: String, constants: String)

    fun getAllProducts(allProductsAdapter: AllProductsAdapter)

    fun getProductInCart(productsAdapter: ProductsAdapter, userId: String)

    fun getProductInOrders(orderAdapter: OrdersAdapter, userId: String)

    fun getProductInOrdersDetails(orderDetailsAdapter: OrderDetailsAdapter, userId: String)

  suspend  fun getAllPrice(userId: String): Float?

  suspend  fun getAllPriceInOrders(userId: String): Float?

  suspend fun checkoutIdProductsInCart(): String
}