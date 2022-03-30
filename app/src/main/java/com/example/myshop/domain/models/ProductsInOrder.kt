package com.example.myshop.domain.models

import java.io.Serializable

data class ProductsInOrder(
    val id: String = "",
    val idOrder: Long =0L,
    val title: String = "",
    val price: Float = 0F,
    val image: String = "",
    val currency: String ="",
    val name: String = "",
    val address: String = "",
    val phoneNumber: Long = 0,
    val zipCode: String = "",
    val notes: String = "",
    val chooseAddress: String = "",
    val time: String = ""
): Serializable