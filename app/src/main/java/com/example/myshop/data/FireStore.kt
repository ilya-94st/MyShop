package com.example.myshop.data

import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class FireStore {

    private val fireStore = FirebaseFirestore.getInstance()

    fun registerUser(registrationFragment: RegistrationFragment, userInfo: Users) {

        fireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnCompleteListener {
                registrationFragment.userRegistrationSuccessful()
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
                }
            }.addOnFailureListener {
                    e->
                when(fragment){
                    is LoginFragment -> {
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

    fun upLoadImageToCloudStorage(fragment: Fragment, imageFileUri: Uri?) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
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
                }
            }
        }.addOnFailureListener{
                execption ->
            when(fragment) {
                is UserProfileFragment -> {
                    fragment.hideProgressDialog()
                }
            }
            Log.e(fragment.activity?.javaClass?.simpleName, execption.message, execption)
        }
    }
}