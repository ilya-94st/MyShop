package com.example.myshop.presentation.ui.fragments.orders_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.*
import com.example.myshop.presentation.adapters.OrderDetailsAdapter
import com.example.myshop.presentation.adapters.OrdersAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersDetailsViewModel @Inject constructor(private val getItemsAddress: GetItemsAddress, private val getUserDetails: CheckUserDetails, private val getOrderDetails: GetOrderDetails,private val getAllPriceInOrders: GetAllPriceInOrders): ViewModel() {
   private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    private var _addressUser = MutableLiveData<List<AddressUser>>()

    var addressUser: LiveData<List<AddressUser>> = _addressUser

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users> = _user

    fun getItemsAddressUser(idUser: String) = viewModelScope.launch {
        _addressUser.postValue(getItemsAddress.invoke(idUser))
    }



    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }

    fun getOrders(ordersDetailsAdapter: OrderDetailsAdapter, userId: String) {
        getOrderDetails.invoke(ordersDetailsAdapter, userId)
    }

    fun getAllPrice(userId: String) = viewModelScope.launch {
        _allPrice.postValue(getAllPriceInOrders.invoke(userId))
    }

}