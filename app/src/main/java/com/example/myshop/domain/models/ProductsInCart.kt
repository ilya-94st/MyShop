package com.example.myshop.domain.models

import java.io.Serializable

data class ProductsInCart(
    val idSeller: String = "",
    val idProduct: Long = 0L,
    val idBuyer: String = "",
    val idOrder: Long = 0L,
    val title: String = "",
    val price: Float = 0F,
    val image: String = "",
    val currency: String ="",
    val quantity: Int = 0,
): Serializable