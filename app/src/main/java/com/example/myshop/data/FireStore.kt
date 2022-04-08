package com.example.myshop.data

import android.net.Uri
import android.util.Log
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
    private var productsInCart = Firebase.firestore.collection(Constants.PRODUCT_IN_CART)

    fun registerUser(userInfo: Users) {
        fireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
    }

    fun updateUserProfileData(userHashMap: HashMap<String, Any>) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
    }

  fun updateProductsData(products: Map<String, Any>, oldProducts: Products) {
     val querySnapshot = fireStore.collection(Constants.PRODUCTS).whereEqualTo("quality", oldProducts.quality).get()
      querySnapshot.addOnSuccessListener {
          try {
          for (document in it) {
              fireStore.collection(Constants.PRODUCTS).document(document.id).set(
                      products, SetOptions.merge())
          }
          } catch (e: Exception) {
              Log.e("updateProductsData", "$e")
          }
      }
    }

    suspend fun updateProductsInCart(products: Map<String, Any>, oldProductsInCart: ProductsInCart) {
        val querySnapshot = productsInCart.whereEqualTo("quantity", oldProductsInCart.quantity).get().await()
        for (document in querySnapshot) {
            try {
                productsInCart.document(document.id).set(
                    products, SetOptions.merge()
                ).await()
            } catch (e: Exception) {
                Log.e("updateProductsInCart", "$e")
            }
        }
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


    suspend fun getAllPrice(userId: String, constants: String): Float? {
        var priceAll: Float? = 0F
        val querySnapshot = fireStore.collection(constants).whereEqualTo("idBuyer", userId).get().await()
         for(document in querySnapshot.documents) {
             val product = document.toObject<ProductsInCart>()
             val price = product?.price
             val quantity = product?.quantity
             if (priceAll != null) {
                 if (price != null) {
                     priceAll += price * quantity!!
                 }
             }
         }
        return priceAll
    }


   suspend  fun getItemsAddress(userId: String): ArrayList<AddressUser> {
        listItemsAddress = arrayListOf()
        val querySnapshot =  fireStore.collection(Constants.ADDRESS_USER).whereEqualTo("idUser", userId).get().await()

                for (document in querySnapshot) {
                        val addressUser = document.toObject<AddressUser>()
                        listItemsAddress.add(addressUser)
                }

         return listItemsAddress
    }



   suspend fun getOrders(idBuyer: String): ArrayList<ProductsInOrder> {
        listOrdersProducts = arrayListOf()
        val querySnapshot = fireStore.collection(Constants.PRODUCTS_IN_ORDERS).whereEqualTo("idBuyer", idBuyer).get().await()
       for (document in querySnapshot) {
           val productInOrder = document.toObject<ProductsInOrder>()
           listOrdersProducts.add(productInOrder)
       }

       return listOrdersProducts
    }

   suspend fun getProducts(userID: String, constants: String): ArrayList<Products> {
        listProducts = arrayListOf()
        val querySnapshot = fireStore.collection(constants).whereEqualTo("idSeller", userID).get().await()
                for (document in querySnapshot) {
                        val product = document.toObject<Products>()
                        listProducts.add(product)
                }
       return listProducts
            }


    suspend fun getProductsInCart(idBuyer: String): ArrayList<ProductsInCart> {
        listProductInCart = arrayListOf()
        val querySnapshot = fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("idBuyer", idBuyer).get().await()
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

    suspend fun getUserMobile(userId: String): Any? {

        val querySnapshot = fireStore.collection(Constants.USERS).whereEqualTo("id", userId).get().await()

        return querySnapshot.documents[0].data?.get("mobile")
    }

    fun deleteProduct(idProduct: Long) {
        val productsQuery =  fireStore.collection(Constants.PRODUCTS).whereEqualTo("idProducts", idProduct)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.PRODUCTS).document(document.id).delete()
            }
        }
    }


     fun deleteAddress(idAddress: Long) {
        val productsQuery =  fireStore.collection(Constants.ADDRESS_USER).whereEqualTo("idAddress", idAddress)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.ADDRESS_USER).document(document.id).delete()
            }
        }
    }

    fun deleteAllProductInCart(idBuyer: String) {
        val productsQuery =  fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("idBuyer", idBuyer)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.PRODUCT_IN_CART).document(document.id).delete()
            }
        }
    }

    fun deleteProductInCart(idBuyer: String, idProduct: Long) {
        val productsQuery =  fireStore.collection(Constants.PRODUCT_IN_CART).whereEqualTo("idBuyer", idBuyer).whereEqualTo("idProduct", idProduct)
            .get()
        productsQuery.addOnSuccessListener {
            for (document in it){
                fireStore.collection(Constants.PRODUCT_IN_CART).document(document.id).delete()
            }
        }
    }

    suspend  fun deleteImage(userId: String)  {
        try {
            val imageDelete = Firebase.storage.reference
            imageDelete.child(
                "${Constants.USER_PRODUCTS_IMAGES}/${userId}"
            ).delete().await()
        } catch (e: IOException) {
            Log.e("deleteImage", "$e")
        }
    }

    suspend fun upLoadImageToCloudStorage(
        userId: String,
        imageFileUri: Uri?,
        constantsImages: String
    ): String {

        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            "${constantsImages}/${userId}"
        )
        sRef.putFile(imageFileUri!!).await()
        val url = sRef.downloadUrl.await()
        return url.toString()
    }


 }