package com.example.myshop.domain.models

import java.io.Serializable

data class Products(
    val id: String = "",
    val title: String = "",
    val price: Float = 0F,
    val description: String = "",
    val quality: Int = 0,
    val image: String = ""
): Serializable