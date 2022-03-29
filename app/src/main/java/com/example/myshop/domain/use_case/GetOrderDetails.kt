package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.OrderDetailsAdapter
import javax.inject.Inject

class GetOrderDetails @Inject constructor(private val productsRepository: ProductsRepository) {

    operator fun  invoke(orderDetailsAdapter: OrderDetailsAdapter, userId: String) {
        productsRepository.getProductInOrdersDetails(orderDetailsAdapter, userId)
    }
}