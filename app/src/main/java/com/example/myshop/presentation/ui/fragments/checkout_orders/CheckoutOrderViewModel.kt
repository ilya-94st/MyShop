package com.example.myshop.presentation.ui.fragments.checkout_orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutOrderViewModel @Inject constructor(private val getProductInCart: GetProductInCart, private val getAllPrice: GetAllPrice, private val checkUserDetails: CheckUserDetails, private val deleteProductsInCart: DeleteProductsInCart): ViewModel() {
    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _products = MutableLiveData<List<Products>>()

    var products: LiveData<List<Products>> = _products


    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getProductInCart(userId: String) = viewModelScope.launch {
       _products.postValue(getProductInCart.invoke(userId))
    }


    fun getAllPrice(userId: String) = viewModelScope.launch {
        _allPrice.postValue(getAllPrice.invoke(userId))
    }

    fun deleteProducts(userId: String) = viewModelScope.launch {
        deleteProductsInCart.invoke(userId)
    }

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _users.postValue(checkUserDetails.invoke())
    }
}