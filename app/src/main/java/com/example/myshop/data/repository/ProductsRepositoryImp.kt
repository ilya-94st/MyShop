package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
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

    override suspend fun deleteAddress(userId: String) {
        FireStore().deleteToId(Constants.ADDRESS_USER, userId)
    }

    override suspend fun deleteProductsInCart(userId: String) {
        FireStore().deleteToId(Constants.PRODUCT_IN_CART, userId)
    }

    override suspend fun getProduct(userId: String, constants: String) =
        FireStore().getProducts(userId, Constants.PRODUCTS)


    override suspend fun getAllProducts() = FireStore().getAllProducts()

    override suspend fun getProductInCart(userId: String) = FireStore().getProductsInCart(userId)


    override suspend fun getProductInOrders(userId: String) = FireStore().getOrders(userId)


    override suspend fun getAllPrice(userId: String): Float? =
        FireStore().getAllPrice(userId, Constants.PRODUCT_IN_CART)

}