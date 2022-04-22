package com.example.myshop.presentation.ui.fragments.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val deleteProducts: DeleteProducts,
    private val deleteImageProduct: DeleteImageProduct,
    private val getProducts: GetProducts
       ): ViewModel() {

    private var _products = MutableLiveData<MutableList<Products>>()

    var products: LiveData<MutableList<Products>> = _products


    fun deleteProduct(idProducts: Long) = viewModelScope.launch {
        deleteProducts.invoke(idProducts)
    }

    fun deleteImage(idProducts: Long) = viewModelScope.launch {
        deleteImageProduct.invoke(idProducts)
    }

    fun getProduct(userId: String) = viewModelScope.launch {
        _products.postValue(getProducts.invoke(userId))
    }
}