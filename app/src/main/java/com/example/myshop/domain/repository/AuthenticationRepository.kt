package com.example.myshop.domain.repository

import com.example.myshop.common.EventClass
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult


interface AuthenticationRepository {

   suspend fun registration(etEmailID: String, etPassword: String, etFirstName: String, etLastName: String)

   suspend fun logInUser(etEmail :String, etPassword: String): EventClass?

   suspend fun checkForgotPassword(etEmail: String)

   fun logout()
}