package com.example.myshop.presentation.ui.fragments.description_products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionProductViewModel @Inject constructor(
    private val getUserDetails: CheckUserDetails,
    private val checkDescriptionsProduct: CheckDescriptionsProduct,
    private val addProductsInCart: AddProductsInCart,
    private val updateProducts: UpdateProducts
): ViewModel() {
    private var number = 1
    private var _quantity = MutableLiveData<Int>()

    var quantity: LiveData<Int> = _quantity

    private var _idOrders = MutableLiveData<Long>()

    var idOrders: LiveData<Long> = _idOrders

    private var _usersMobile = MutableLiveData<Any>()

    var usersMobile: LiveData<Any> = _usersMobile

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    fun updateProducts(oldProduct: Products, quantity: Int) = viewModelScope.launch {
        updateProducts.invoke(oldProduct, quantity)
    }

    fun getUserMobile(usersId: String) = viewModelScope.launch {
        _usersMobile.postValue(checkDescriptionsProduct.invoke(usersId))
    }

    fun addProductInCart(productsInCart: ProductsInCart) = viewModelScope.launch {
        addProductsInCart.invoke(productsInCart)
    }

   private fun getUsers() = viewModelScope.launch {
        _users.postValue(getUserDetails.invoke())
    }

    private fun getOrdersId() {
        _idOrders.value = System.currentTimeMillis()
    }

    fun plusQuantity() {
        _quantity.value = ++ number
    }

    fun minusQuantity() {
        _quantity.value = -- number
    }

    init {
        _quantity.value = 1
        getUsers()
        getOrdersId()
    }
}