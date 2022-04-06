package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.repository.ProductsRepository
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

    override  fun deleteProduct(idProduct: Long) {
        FireStore().deleteProduct(idProduct)
    }

    override suspend fun deleteImageProduct(fileExtension: String) {
        FireStore().deleteImage(fileExtension)
    }

    override  fun deleteAddress(idAddress: Long) {
        FireStore().deleteAddress(idAddress)
    }

    override  fun deleteAllProductsInCart(idBuyer: String) {
        FireStore().deleteAllProductInCart(idBuyer)
    }

    override fun deleteProductInCart(idBuyer: String, idProduct: Long) {
        FireStore().deleteProductInCart(idBuyer, idProduct)
    }


    override suspend fun getProduct(idSeller: String, constants: String) =
        FireStore().getProducts(idSeller, Constants.PRODUCTS)


    override suspend fun getAllProducts() = FireStore().getAllProducts()

    override suspend fun getProductInCart(idBuyer: String) = FireStore().getProductsInCart(idBuyer)


    override suspend fun getProductInOrders(idBuyer: String) = FireStore().getOrders(idBuyer)


    override suspend fun getAllPrice(userId: String): Float? =
        FireStore().getAllPrice(userId, Constants.PRODUCT_IN_CART)

}