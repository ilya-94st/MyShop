package com.example.myshop.domain.models

data class ProductsInCart(
    val id: String = "",
    val title: String = "",
    val price: Float = 0F,
    val priceSum: Float =0F,
    val quality: Int = 0,
    val image: String = "",
    val currency: String =""
)