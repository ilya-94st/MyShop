package com.example.myshop.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult


interface AuthenticationRepository {

   suspend fun registration(etEmailID: String, etPassword: String, etFirstName: String, etLastName: String)

   suspend fun logInUser(etEmail :String, etPassword: String)

   suspend fun checkForgotPassword(etEmail: String)

   fun checkUserRegister(etEmail: String, etPassword: String): Boolean
}