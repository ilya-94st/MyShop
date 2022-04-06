package com.example.myshop.presentation.ui.fragments.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.GetOrders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val getOrders: GetOrders, private val getUserDetails: CheckUserDetails): ViewModel() {
private var _user = MutableLiveData<Users>()
var user: LiveData<Users> = _user

    private var _orders = MutableLiveData<List<ProductsInOrder>>()

    var order: LiveData<List<ProductsInOrder>> = _orders

    fun getOrders(userId: String) = viewModelScope.launch {
        _orders.postValue(getOrders.invoke(userId))
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }

    init {
        getUser()
    }

}