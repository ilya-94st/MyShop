package com.example.myshop.common

import com.example.myshop.domain.models.Products

sealed class EventClass {
        data class ErrorIn(val error: String) :  EventClass()
        object Success :  EventClass()
        object Empty:  EventClass()
        data class SuccessList(val list: ArrayList<Products>) :  EventClass()
}