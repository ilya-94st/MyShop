package com.example.myshop.domain.repository

import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface ProductsRepository {


   suspend fun addProducts(products: Products)

   suspend fun addProductsInCart(products: ProductsInCart, constants: String)

   suspend fun addProductInOrders(productsInOrder: ProductsInOrder)

    fun deleteProduct(idProduct: Long)

   suspend fun deleteImageProduct(idProducts: Long)

    fun deleteAddress(idAddress: Long)

    fun deleteAllProductsInCart(idBuyer: String)

    fun deleteProductInCart(idOrder: Long)

   suspend fun getProduct(idSeller: String): ArrayList<Products>

   suspend fun getProductIdProduct(idProduct: Long): ArrayList<Products>

   suspend fun getAllProducts(): Task<QuerySnapshot>

   suspend fun getProductInCart(idBuyer: String): ArrayList<ProductsInCart>

   suspend fun getProductInOrders(idBuyer: String): ArrayList<ProductsInOrder>

   suspend fun getProductQuantityInCart(userId: String, idOrder: Long): Int?
}