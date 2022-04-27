package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.common.EventClass
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.UpdateRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateRepositoryImp @Inject constructor(private val fireStore: FirebaseFirestore): UpdateRepository {

    override fun updateUserProfileData(userHashMap: HashMap<String, Any>): EventClass? {
        var registerResult: EventClass? = null

        try {
            registerResult = EventClass.Success
            FireStore().updateUserProfileData(userHashMap)
        } catch (e: Exception){
        registerResult =  EventClass.ErrorIn("${e.message}")
        }
    return registerResult
    }

    override  fun upDataProducts(products: Map<String, Any>, oldProducts: Products) {
        val querySnapshot = fireStore.collection(Constants.PRODUCTS).whereEqualTo("quality", oldProducts.quantity).get()
        querySnapshot.addOnSuccessListener {
                for (document in it) {
                    fireStore.collection(Constants.PRODUCTS).document(document.id).set(
                        products, SetOptions.merge())
                }
        }
    }

    override suspend fun upDataProductsInCart(
        products: Map<String, Any>,
        oldQuantity: Int,
        idOrder: Long
    ) {
        val querySnapshot = fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("quantity", oldQuantity).whereEqualTo("idOrder", idOrder).get().await()
        for (document in querySnapshot) {
                fireStore.collection(Constants.PRODUCT_IN_CART).document(document.id).set(
                    products, SetOptions.merge()
                ).await()

        }
    }
}