package com.example.myshop.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult


interface AuthenticationRepository {

   suspend fun registration(etEmailID: String, etPassword: String, etFirstName: String, etLastName: String)

   fun logInUser(etEmail :String, etPassword: String): Task<AuthResult>

   suspend fun checkForgotPassword(etEmail: String)

}