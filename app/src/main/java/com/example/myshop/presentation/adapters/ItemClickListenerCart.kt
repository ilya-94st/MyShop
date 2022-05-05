package com.example.myshop.presentation.adapters

import com.example.myshop.domain.models.ProductsInCart

interface ItemClickListenerCart {
    fun minus(productsInCart: ProductsInCart, position:Int)
    fun add(productsInCart: ProductsInCart, position:Int)
    fun deleteItem(productsInCart: ProductsInCart)
}