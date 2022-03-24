package com.example.myshop.domain.models

data class ProductsInCart(
    val id: String = "",
    val title: String = "",
    val price: Float = 0F,
    val image: String = "",
    val currency: String =""
)