package com.example.myshop.presentation.ui.fragments.orders_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderDetailsViewModel : ViewModel() {

    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getAllPriceInCart(quantityInOrder: Int, price: Float) {
        val priceAll = price * quantityInOrder
        _allPrice.value = priceAll
    }
}