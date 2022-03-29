package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class GetAllPriceInOrders @Inject constructor(private val productsRepository: ProductsRepository) {

  suspend  operator fun invoke(userId: String) = productsRepository.getAllPriceInOrders(userId)
}