package com.example.myshop.presentation.ui.fragments.description_products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionProductViewModel @Inject constructor(
    private val checkDescriptionsProduct: CheckDescriptionsProduct,
    private val addProductsInCart: AddProductsInCart
): ViewModel() {

    private var _idOrders = MutableLiveData<Long>()

    var idOrders: LiveData<Long> = _idOrders

    private var _usersMobile = MutableLiveData<Any>()

    var usersMobile: LiveData<Any> = _usersMobile

    fun getUserMobile(usersId: String) = viewModelScope.launch {
        _usersMobile.postValue(checkDescriptionsProduct.invoke(usersId))
    }

    fun addProductInCart(productsInCart: ProductsInCart) = viewModelScope.launch {
        addProductsInCart.invoke(productsInCart)
    }

    private fun getOrdersId() {
        _idOrders.value = System.currentTimeMillis()
    }


    init {
        getOrdersId()
    }
}