package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class DeleteAllProductsInCart @Inject constructor(private val productsRepository: ProductsRepository) {

     operator fun invoke(idBuyer: String) {
        productsRepository.deleteAllProductsInCart(idBuyer)
    }
}