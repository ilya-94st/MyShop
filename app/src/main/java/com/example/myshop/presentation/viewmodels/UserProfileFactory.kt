package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.domain.use_case.GetUserProfile

@Suppress("UNCHECKED_CAST")
class UserProfileFactory(private val getUserProfile: GetUserProfile)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserProfileViewModel::class.java)){
            return UserProfileViewModel(getUserProfile) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}