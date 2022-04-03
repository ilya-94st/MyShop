package com.example.myshop.presentation.ui.fragments.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.Constants
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.DeleteImageProduct
import com.example.myshop.domain.use_case.DeleteProducts
import com.example.myshop.domain.use_case.GetProducts
import com.example.myshop.presentation.adapters.ProductsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val deleteProducts: DeleteProducts, private val deleteImageProduct: DeleteImageProduct, private val getProducts: GetProducts, private val checkUserDetails: CheckUserDetails): ViewModel() {
    private var _products = MutableLiveData<List<Products>>()

    var products: LiveData<List<Products>> = _products

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    fun deleteProduct(userId: String) = viewModelScope.launch {
        deleteProducts.invoke(userId)
    }

    fun deleteImage(userId: String) = viewModelScope.launch {
        deleteImageProduct.invoke(userId)
    }

    fun getProduct(userId: String) = viewModelScope.launch {
        _products.postValue(getProducts.invoke(userId))
    }



    private fun getUsers() = viewModelScope.launch {
        _users.postValue(checkUserDetails.invoke())
    }

    init {
        getUsers()
    }
}