package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class GetQuantityInCart @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(userId: String, idOrder: Long) = productsRepository.getProductQuantityInCart(userId, idOrder)
}