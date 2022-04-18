package com.example.myshop.data.repository

import android.annotation.SuppressLint
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryIml @Inject constructor(private val firebaseAuth: FirebaseAuth, private val fireStore: FirebaseFirestore): AuthenticationRepository {

    @SuppressLint("LongLogTag")
    override suspend fun registration(
        etEmailID: String,
        etPassword: String
    ) = firebaseAuth.createUserWithEmailAndPassword(etEmailID, etPassword)

    override suspend fun logInUser(etEmail: String, etPassword: String) = firebaseAuth.signInWithEmailAndPassword(etEmail, etPassword)


    override suspend fun checkForgotPassword(etEmail: String) {
       firebaseAuth.sendPasswordResetEmail(etEmail).await()
    }

    override fun logout() {
       FirebaseAuth.getInstance().signOut()
    }

    override fun registerUser(userInfo: Users) {
        fireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
    }

}