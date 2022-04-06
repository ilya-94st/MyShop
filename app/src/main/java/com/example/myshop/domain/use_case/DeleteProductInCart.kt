package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class DeleteProductInCart @Inject constructor(private val productsRepository: ProductsRepository) {

   operator fun invoke(idBuyer: String, idProduct: Long) {
        productsRepository.deleteProductInCart(idBuyer, idProduct)
    }
}