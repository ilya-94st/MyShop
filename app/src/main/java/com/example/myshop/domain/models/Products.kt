package com.example.myshop.domain.models

import java.io.Serializable

data class Products(
    val idSeller: String = "",
    val idProducts: Long = 0L,
    val title: String = "",
    val price: Float? = 0F,
    val description: String = "",
    val quantity: Int? = 0,
    val image: String = "",
    val currency: String =""
): Serializable