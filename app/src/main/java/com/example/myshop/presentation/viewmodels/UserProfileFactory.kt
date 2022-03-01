package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.domain.use_case.CheckMobile

@Suppress("UNCHECKED_CAST")
class UserProfileFactory(private val checkMobile: CheckMobile)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserProfileViewModel::class.java)){
            return UserProfileViewModel(checkMobile) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}