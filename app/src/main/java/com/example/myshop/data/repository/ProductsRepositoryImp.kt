package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.repository.ProductsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(private val fireStore: FirebaseFirestore): ProductsRepository {

    override suspend fun getAllProducts() = fireStore.collection(Constants.PRODUCTS).get()

   override suspend  fun addProducts(products: Products) {
            fireStore.collection(Constants.PRODUCTS).add(products).await()
    }

    override suspend  fun addProductsInCart(products: ProductsInCart, constants: String) {
            fireStore.collection(constants).add(products).await()
    }

    override suspend fun addProductInOrders(productsInOrder: ProductsInOrder) {
            fireStore.collection(Constants.PRODUCTS_IN_ORDERS).add(productsInOrder).await()
    }

    override fun deleteProduct(idProduct: Long) {
        val productsQuery =  fireStore.collection(Constants.PRODUCTS).whereEqualTo("idProducts", idProduct)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.PRODUCTS).document(document.id).delete()
            }
        }
    }

    override  suspend  fun deleteImageProduct(idProducts: Long)  {
            val imageDelete = Firebase.storage.reference
            imageDelete.child(
                "${Constants.USER_PRODUCTS_IMAGES}/${idProducts}"
            ).delete().await()
    }

    override fun deleteAddress(idAddress: Long) {
        val productsQuery =  fireStore.collection(Constants.ADDRESS_USER).whereEqualTo("idAddress", idAddress)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.ADDRESS_USER).document(document.id).delete()
            }
        }
    }

    override fun deleteAllProductsInCart(idBuyer: String) {
        val productsQuery =  fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("idBuyer", idBuyer)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.PRODUCT_IN_CART).document(document.id).delete()
            }
        }
    }
    override fun deleteProductInCart(idOrder: Long) {
        val productsQuery =  fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("idOrder", idOrder)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.PRODUCT_IN_CART).document(document.id).delete()
            }
        }
    }

    override suspend fun getProduct(idSeller: String) = fireStore.collection(Constants.PRODUCTS).whereEqualTo("idSeller", idSeller).get()

    override suspend fun getProductIdProduct(
        idProduct: Long
    ): ArrayList<Products> {
        val listProducts: ArrayList<Products> = arrayListOf()
        val querySnapshot = fireStore.collection(Constants.PRODUCTS).whereEqualTo("idProducts", idProduct).get().await()
        for (document in querySnapshot) {
            val product = document.toObject<Products>()
            listProducts.add(product)
        }
        return listProducts
    }

    override  suspend fun getProductInCart(idBuyer: String) = fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("idBuyer", idBuyer).get()

    override suspend fun getProductInOrders(idBuyer: String) = fireStore.collection(Constants.PRODUCTS_IN_ORDERS).whereEqualTo("idBuyer", idBuyer).get()

    override suspend fun getProductQuantityInCart(userId: String, idOrder: Long): Int {
        val querySnapshot = fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("idBuyer", userId).whereEqualTo("idOrder", idOrder).get().await()

        return querySnapshot.documents[0].data?.get("quantity").toString().toInt()

    }
}