package com.example.myshop.presentation.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.use_case.GetAllProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val getAllProducts: GetAllProducts): ViewModel() {
    private var _products = MutableLiveData<List<Products>>()

    var products: LiveData<List<Products>> = _products


    fun getAllProducts() = viewModelScope.launch {
        _products.postValue(getAllProducts.invoke())
    }
}