package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.domain.use_case.CheckLogin

@Suppress("UNCHECKED_CAST")
class LoginFactoryViewModel(private val checkLogin: CheckLogin)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(checkLogin) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}