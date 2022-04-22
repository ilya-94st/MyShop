package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class GetAllPriceInCart @Inject constructor(private val productsRepository: ProductsRepository) {

 suspend operator fun invoke(userId: String, quantity: Int)  = productsRepository.getAllPriceInCart(userId, quantity)


}