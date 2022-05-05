package com.example.myshop.common

import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder

sealed class EventClass {
        data class ErrorIn(val error: String) :  EventClass()
        object Success :  EventClass()
        data class GetAllProducts(val list: ArrayList<Products>) :  EventClass()
        data class GetProducts(val list: ArrayList<Products>) :  EventClass()
        data class GetProductsInCart(val list: ArrayList<ProductsInCart>) :  EventClass()
        data class GetOrders(val list: ArrayList<ProductsInOrder>) :  EventClass()
        data class GetAddressItems(val list: ArrayList<AddressUser>) :  EventClass()

}