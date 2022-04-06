package com.example.myshop.domain.models

import java.io.Serializable

data class AddressUser(
    val idUser: String = "",
    val idAddress: Long = 0L,
    val name: String = "",
    val address: String = "",
    val phoneNumber: Long = 0,
    val zipCode: String = "",
    val notes: String = "",
    val chooseAddress: String = ""
): Serializable