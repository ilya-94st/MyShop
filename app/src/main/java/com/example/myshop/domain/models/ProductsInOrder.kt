package com.example.myshop.domain.models

data class ProductsInOrder(
    val id: String = "",
    val idOrder: Long =0L,
    val title: String = "",
    val price: Float = 0F,
    val image: String = "",
    val currency: String =""
)