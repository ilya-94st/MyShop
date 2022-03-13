package com.example.myshop.domain.repository

import com.example.myshop.presentation.ui.fragments.LoginFragment

interface AuthenticationRepository {

   suspend fun registration(etEmailID: String, etPassword: String, etFirstName: String, etLastName: String)

   suspend fun logInUser(fragment: LoginFragment, etEmail :String, etPassword: String)

   suspend fun checkForgotPassword(etEmail: String)
}