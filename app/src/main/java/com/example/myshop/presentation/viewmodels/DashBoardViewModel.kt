package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myshop.domain.use_case.GetProducts
import com.example.myshop.presentation.adapters.AllProductsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val getProducts: GetProducts): ViewModel() {

    fun getAllProducts(allProductsAdapter: AllProductsAdapter){
        getProducts.getAllProducts(allProductsAdapter)
    }
}