package com.example.myshop.data

import android.net.Uri
import android.util.Log
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.Users
import com.example.myshop.presentation.adapters.AddressAdapter
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.IOException

class FireStore {

    private val fireStore = FirebaseFirestore.getInstance()
    private lateinit var listProducts: ArrayList<Products>
    private lateinit var listAllProducts: ArrayList<Products>
    private lateinit var listItemsAddress: ArrayList<AddressUser>



    fun registerUser(userInfo: Users) {

        fireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
    }

    private  fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    suspend fun getUserDetails(): Users {
        val userDetails = fireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get().await()

        return userDetails.toObject(Users::class.java)!!
    }



    fun updateUserProfileData(userHashMap: HashMap<String, Any>) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
    }

  suspend  fun addProducts(products: Products, constants: String) {
            try {

                fireStore.collection(constants).add(products).await()

            } catch (e: IOException) {
                   Log.e("addProducts", "error addProducts")
            }
    }

    suspend  fun addProductsInCart(products: ProductsInCart, constants: String) {
        try {

            fireStore.collection(constants).add(products).await()

        } catch (e: IOException) {
            Log.e("addProducts", "error addProductsInCart")
        }
    }

    suspend fun addAddressItems(addressUser: AddressUser) {
        try {

            fireStore.collection(Constants.ADDRESS_USER).add(addressUser).await()

        } catch (e: IOException) {
            Log.e("address", "error addAddressItems")
        }
    }

   suspend fun getAllPrice(userId: String, constants: String): Float? {
        var priceAll: Float? = 0F
        val querySnapshot = fireStore.collection(constants).whereEqualTo("id", userId).get().await()
         for(document in querySnapshot.documents) {
             val product = document.toObject<ProductsInCart>()
             val price = product?.price
             if (priceAll != null) {
                 if (price != null) {
                     priceAll += price
                 }
             }
         }
        return priceAll
    }

     fun getItemsAddress(addressAdapter: AddressAdapter, userId: String) {
        listItemsAddress = arrayListOf()
        fireStore.collection(Constants.ADDRESS_USER).whereEqualTo("id", userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        listItemsAddress.add(dc.document.toObject(AddressUser::class.java))
                        addressAdapter.submitList(listItemsAddress)
                    }
                }
            }
    }

    fun getProducts(productsAdapter: ProductsAdapter, userID: String, constants: String) {
        listProducts = arrayListOf()
        fireStore.collection(constants).whereEqualTo("id", userID)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        listProducts.add(dc.document.toObject(Products::class.java))
                        productsAdapter.submitList(listProducts)
                    }
                }
            }
    }


    fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        listAllProducts = arrayListOf()
        fireStore.collection(Constants.PRODUCTS)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        listAllProducts.add(dc.document.toObject(Products::class.java))
                        allProductsAdapter.submitList(listAllProducts)
                    }
                }
            }
    }


        suspend fun deleteProducts() {
           val productsQuery =  fireStore.collection(Constants.PRODUCTS)
                 .get()
                 .await()
             if (productsQuery.documents.isNotEmpty()) {
                 for (document in productsQuery) {
                     try {
                         fireStore.collection(Constants.PRODUCTS).document(document.id).delete().await()
                     } catch (e: IOException) {
                         Log.e("deleteProducts", "$e")
                     }
                 }
             }
         }

    fun upLoadImageToCloudStorage(userId: String, imageFileUri: Uri?, constantsImages: String): UploadTask {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            constantsImages
                    + "." + userId
        )
        return sRef.putFile(imageFileUri!!)
    }

  suspend  fun deleteImage(userId: String)  {
        try {
            val imageDelete = Firebase.storage.reference
            imageDelete.child(Constants.USER_PRODUCTS_IMAGES + "."
                    + userId).delete().await()
        } catch (e: IOException) {
            Log.e("deleteImage", "$e")
        }
    }

    suspend fun getUserMobile(userId: String): Any? {

        val querySnapshot = fireStore.collection(Constants.USERS).whereEqualTo("id", userId).get().await()

        return querySnapshot.documents[0].data?.get("mobile")
    }


}