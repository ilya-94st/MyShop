package com.example.myshop.data

import com.example.myshop.common.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class FireStore {
    private val fireStore = FirebaseFirestore.getInstance()

    fun updateUserProfileData(userHashMap: HashMap<String, Any>) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
    }

      fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
 }