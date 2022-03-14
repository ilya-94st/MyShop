package com.example.myshop.domain.repository


interface AuthenticationRepository {

   suspend fun registration(etEmailID: String, etPassword: String, etFirstName: String, etLastName: String)

   suspend fun logInUser(etEmail :String, etPassword: String)

   suspend fun checkForgotPassword(etEmail: String)
}