package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.domain.use_case.CheckRegistration

@Suppress("UNCHECKED_CAST")
class RegistrationFactoryViewModel(private val checkRegistration: CheckRegistration)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegistrationViewModel::class.java)){
            return RegistrationViewModel(checkRegistration) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}