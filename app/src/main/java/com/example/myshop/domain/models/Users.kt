package com.example.myshop.domain.models

import java.io.Serializable

data class Users(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val profileCommitted: Int = 0
): Serializable