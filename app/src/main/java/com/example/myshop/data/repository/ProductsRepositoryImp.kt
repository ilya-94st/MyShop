package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.OrderDetailsAdapter
import com.example.myshop.presentation.adapters.OrdersAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(): ProductsRepository {
    override suspend fun addProducts(products: Products) {
        FireStore().addProducts(products, Constants.PRODUCTS)
    }

    override suspend fun addProductsInCart(products: ProductsInCart, constants: String) {
       FireStore().addProductsInCart(products, Constants.PRODUCT_IN_CART)
    }

    override suspend fun addProductInOrders(productsInOrder: ProductsInOrder) {
        FireStore().addProductsInOrders(productsInOrder)
    }

    override suspend fun deleteProduct(constants: String) {
        FireStore().deleteProducts(constants)
    }

    override suspend fun deleteImageProduct(fileExtension: String) {
        FireStore().deleteImage(fileExtension)
    }

    override suspend fun deleteProductsInCart(userId: String) {
        FireStore().deleteProductsInCart(Constants.PRODUCT_IN_CART, userId)
    }

    override fun getProduct(productsAdapter: ProductsAdapter, userId: String, constants: String) {
        FireStore().getProducts(productsAdapter, userId, Constants.PRODUCTS)
    }

    override fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        FireStore().getAllProducts(allProductsAdapter)
    }

    override fun getProductInCart(
        productsAdapter: ProductsAdapter,
        userId: String
    ) {
        FireStore().getProducts(productsAdapter, userId, Constants.PRODUCT_IN_CART)
    }

    override fun getProductInOrders(orderAdapter: OrdersAdapter, userId: String) {
        FireStore().getOrders(orderAdapter, userId)
    }

    override fun getProductInOrdersDetails(
        orderDetailsAdapter: OrderDetailsAdapter,
        userId: String
    ) {
        FireStore().getOrderDetails(orderDetailsAdapter, userId)
    }


    override suspend fun getAllPrice(userId: String): Float? =
        FireStore().getAllPrice(userId, Constants.PRODUCT_IN_CART)

    override suspend fun getAllPriceInOrders(userId: String) = FireStore().getAllPriceInOrders(userId)

    override suspend fun checkoutIdProductsInCart() = FireStore().getIdProductsInCart()

}