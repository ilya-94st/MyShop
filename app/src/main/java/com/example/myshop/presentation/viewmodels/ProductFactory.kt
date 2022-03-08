package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.domain.use_case.AddProducts

@Suppress("UNCHECKED_CAST")
class ProductFactory(private val addProducts: AddProducts)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(addProducts) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}