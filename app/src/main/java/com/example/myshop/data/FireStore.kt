package com.example.myshop.data

import android.net.Uri
import android.util.Log
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.*
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
    private lateinit var listOrdersProducts: ArrayList<ProductsInOrder>
    private lateinit var listProductInCart: ArrayList<ProductsInCart>




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

    suspend  fun addProductsInOrders(products: ProductsInOrder) {
        try {

            fireStore.collection(Constants.PRODUCTS_IN_ORDERS).add(products).await()

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


   suspend  fun getItemsAddress(userId: String): ArrayList<AddressUser> {
        listItemsAddress = arrayListOf()
        val querySnapshot =  fireStore.collection(Constants.ADDRESS_USER).whereEqualTo("id", userId).get().await()

                for (document in querySnapshot) {
                        val addressUser = document.toObject<AddressUser>()
                        listItemsAddress.add(addressUser)
                }

         return listItemsAddress
    }



   suspend fun getOrders(userId: String): ArrayList<ProductsInOrder> {
        listOrdersProducts = arrayListOf()
        val querySnapshot = fireStore.collection(Constants.PRODUCTS_IN_ORDERS).whereEqualTo("id", userId).get().await()
       for (document in querySnapshot) {
           val productInOrder = document.toObject<ProductsInOrder>()
           listOrdersProducts.add(productInOrder)
       }

       return listOrdersProducts
    }

   suspend fun getProducts(userID: String, constants: String): ArrayList<Products> {
        listProducts = arrayListOf()
        val querySnapshot = fireStore.collection(constants).whereEqualTo("id", userID).get().await()
                for (document in querySnapshot) {
                        val product = document.toObject<Products>()
                        listProducts.add(product)
                }
       return listProducts
            }


    suspend fun getProductsInCart(userID: String): ArrayList<ProductsInCart> {
        listProductInCart = arrayListOf()
        val querySnapshot = fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("id", userID).get().await()
        for (document in querySnapshot) {
            val product = document.toObject<ProductsInCart>()
            listProductInCart.add(product)
        }
        return listProductInCart
    }

   suspend fun getAllProducts(): ArrayList<Products> {
        listAllProducts = arrayListOf()
       val querySnapshot = fireStore.collection(Constants.PRODUCTS).get().await()
       for (document in querySnapshot) {
           val product = document.toObject<Products>()
           listAllProducts.add(product)
       }
       return listAllProducts
    }


        suspend fun deleteProducts(constants: String) {
           val productsQuery =  fireStore.collection(constants)
                 .get()
                 .await()
             if (productsQuery.documents.isNotEmpty()) {
                 for (document in productsQuery) {
                     try {
                         fireStore.collection(constants).document(document.id).delete().await()
                     } catch (e: IOException) {
                         Log.e("deleteProducts", "$e")
                     }
                 }
             }
         }

    suspend fun deleteToId(constants: String, userId: String) {
        val productsQuery =  fireStore.collection(constants).whereEqualTo("id", userId)
            .get()
            .await()
        if (productsQuery.documents.isNotEmpty()) {
            for (document in productsQuery) {
                try {
                    fireStore.collection(constants).document(document.id).delete().await()
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