package com.example.myshop.data

import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.Users
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.*
import com.example.myshop.presentation.ui.prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

class FireStore {

    private val fireStore = FirebaseFirestore.getInstance()
    private lateinit var listProducts: ArrayList<Products>
    private lateinit var listAllProducts: ArrayList<Products>


    fun registerUser(registrationFragment: RegistrationFragment, userInfo: Users) {

        fireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnCompleteListener {
                registrationFragment.hideProgressDialog()
            }
            .addOnFailureListener {
                registrationFragment.hideProgressDialog()
                Log.e("registration", "Error while registering the user")
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

    fun getUsersDetails(fragment: Fragment) {

        fireStore.collection(Constants.USERS)

            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(fragment.activity?.javaClass?.simpleName, document.toString())

                val user = document.toObject(Users::class.java)!!


                prefs.name = "${user.firstName} ${user.lastName}"

                when(fragment) {
                    is LoginFragment -> {
                        fragment.userLoggedInSuccessful(user)
                    }
                    is SettingsFragment -> {
                        fragment.userDetailsSuccessful(user)
                    }
                    is AddProductsFragment -> {
                        fragment.userDetailsSuccessful(user)
                    }
                    is ProductsFragment -> {
                        fragment.userDetailsSuccessful(user)
                    }
                }
            }.addOnFailureListener {
                    e->
                when(fragment){
                    is LoginFragment -> {
                        fragment.hideProgressDialog()
                    }
                    is SettingsFragment -> {
                        fragment.hideProgressDialog()
                    }

                }
                Log.e("registration2","Error while registering the user $e")
            }
    }

    fun updateUserProfileData(fragment: Fragment, userHashMap: HashMap<String, Any>) {

        fireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when(fragment) {
                    is UserProfileFragment -> {

                        fragment.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { error ->
                when(fragment) {
                    is UserProfileFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("registration2","Error while registering the user $error")
            }
    }

    fun upLoadImageToCloudStorage(fragment: Fragment, imageFileUri: Uri?, constantsImages: String) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            constantsImages + System.currentTimeMillis() + "."
                    + getFileExtension(
                fragment, imageFileUri
            )
        )
        sRef.putFile(imageFileUri!!).addOnSuccessListener { taskSnapShot ->
            // the image upload is success
            Log.e("Firebase image Url", taskSnapShot.metadata!!.reference!!.downloadUrl.toString())

            taskSnapShot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri->
                Log.e("Download image Url", uri.toString())

                when(fragment) {
                    is UserProfileFragment -> {
                        fragment.imageUploadSuccess(uri.toString())
                    }
                    is AddProductsFragment -> {
                        fragment.addProductsImageSuccessful(uri.toString())
                    }

                }
            }
        }.addOnFailureListener{
                execption ->
            when(fragment) {
                is UserProfileFragment -> {
                    fragment.hideProgressDialog()
                }
                is AddProductsFragment -> {
                    fragment.hideProgressDialog()
                }
            }
            Log.e(fragment.activity?.javaClass?.simpleName, execption.message, execption)
        }
    }

    private fun getFileExtension(fragment: Fragment, uri: Uri?): String? {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fragment.activity?.contentResolver?.getType(uri!!))
    }

    fun addProducts(fragment: AddProductsFragment, products: Products, constants: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {

                fireStore.collection(constants).add(products).await()

            } catch (e: IOException) {
                withContext(Dispatchers.Main){
                    fragment.toast("error addProducts")
                }
            }
        }
    }

    fun getProducts(productsAdapter: ProductsAdapter, userID: String) {
        listProducts = arrayListOf()
        fireStore.collection(Constants.PRODUCTS).whereEqualTo("id", userID)
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


         fun deleteProducts(productsFragment: ProductsFragment) = CoroutineScope(Dispatchers.IO).launch {
           val productsQuery =  fireStore.collection(Constants.PRODUCTS)
                 .get()
                 .await()
             if (productsQuery.documents.isNotEmpty()) {
                 for (document in productsQuery) {
                     try {
                         fireStore.collection(Constants.PRODUCTS).document(document.id).delete().await()
                     } catch (e: IOException) {
                         productsFragment.toast("$e")
                     }
                 }
             }
         }

    fun deleteImage(productsFragment: ProductsFragment) = CoroutineScope(Dispatchers.IO).launch {
        try {
            FirebaseStorage.getInstance().reference.child(Constants.USER_PRODUCTS_IMAGES).delete().await()
        } catch (e: IOException) {
            productsFragment.toast("$e")
        }
    }

    fun getUserMobile(descriptionProductFragment: DescriptionProductFragment, userId: String) = CoroutineScope(Dispatchers.IO).launch {
            try {
                fireStore.collection(Constants.USERS).whereEqualTo("id", userId)
                    .get()
                    .addOnSuccessListener { document ->

                        val userMobile = document.documents[0].data?.get("mobile")

                        if (userMobile != null) {
                            descriptionProductFragment.userMobileSuccessful(userMobile)
                        }

                    }.await()
            } catch (e: IOException){
                withContext(Dispatchers.Main){
                    descriptionProductFragment.toast("$e")
                }
            }
    }
}