package com.example.myshop.presentation.ui.fragments.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.AddProductInOrder
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.GetIdInProductsInCart
import com.example.myshop.domain.use_case.GetOrders
import com.example.myshop.presentation.adapters.OrdersAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val getOrders: GetOrders, private val getUserDetails: CheckUserDetails, private val getIdInProductsInCart: GetIdInProductsInCart): ViewModel() {
private var _user = MutableLiveData<Users>()
var user: LiveData<Users> = _user

    private var _orders = MutableLiveData<List<ProductsInOrder>>()

    var order: LiveData<List<ProductsInOrder>> = _orders

    private var _response = MutableLiveData<Boolean>()

    var response: LiveData<Boolean> = _response

    fun getOrders(userId: String) = viewModelScope.launch {
        _orders.postValue(getOrders.invoke(userId))
    }



    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }

    init {
        getUser()
    }

    fun getIdProductInCart() = viewModelScope.launch {
        _response.value = true
    }
}