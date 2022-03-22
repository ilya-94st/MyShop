package com.example.myshop.common

sealed class EventClass {
        data class ErrorIn(val error: String) :  EventClass()
        object Success :  EventClass()
        object Empty:  EventClass()
}