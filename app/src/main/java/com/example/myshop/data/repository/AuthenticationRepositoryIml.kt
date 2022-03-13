package com.example.myshop.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.repository.AuthenticationRepository
import com.example.myshop.presentation.ui.fragments.LoginFragment
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
        val email = etEmailID.trim { it <= ' ' }
        val password = etPassword.trim { it <= ' ' }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
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

    override suspend fun logInUser(fragment: LoginFragment, etEmail: String, etPassword: String) {
        val email = etEmail.trim { it <= ' ' }
        val password = etPassword.trim { it <= ' ' }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FireStore().getUsersDetails(fragment)
                }
            }.await()
    }

    override suspend fun checkForgotPassword(etEmail: String) {
        val email = etEmail.trim { it <= ' ' }
            firebaseAuth.sendPasswordResetEmail(email).await()
    }
}