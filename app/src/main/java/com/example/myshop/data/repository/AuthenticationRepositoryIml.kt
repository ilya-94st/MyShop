package com.example.myshop.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.myshop.common.EventClass
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryIml @Inject constructor(private val firebaseAuth: FirebaseAuth): AuthenticationRepository {


    @SuppressLint("LongLogTag")
    override suspend fun registration(
        etEmailID: String,
        etPassword: String,
        etFirstName: String,
        etLastName: String
    ) {


        firebaseAuth.createUserWithEmailAndPassword(etEmailID, etPassword)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val firebaseUser: FirebaseUser = task.result!!.user!!

                    val user = Users(
                        firebaseUser.uid,
                        etFirstName,
                        etLastName,
                        etEmailID,
                        )

                    FireStore().registerUser(user)
                } else {
                    Log.e("AuthenticationRepository", task.exception!!.message.toString())
                }
            }.await()

    }

    override suspend fun logInUser(etEmail: String, etPassword: String): EventClass? {
        var registerResult: EventClass? = null

        try {
            firebaseAuth.signInWithEmailAndPassword(etEmail, etPassword).addOnCompleteListener {
                task ->
                registerResult = if(task.isSuccessful) {
                    EventClass.Success
                } else {
                    EventClass.ErrorIn("${task.exception?.message}")
                }
            }.await()
        }catch (e: Exception) {
            registerResult =  EventClass.ErrorIn("${e.message}")
        }

       return registerResult
    }

    override suspend fun checkForgotPassword(etEmail: String) {
            firebaseAuth.sendPasswordResetEmail(etEmail).await()
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

}