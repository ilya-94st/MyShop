package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class DeleteProducts @Inject constructor(private val productsRepository: ProductsRepository) {

  suspend operator fun invoke() {
       productsRepository.deleteProduct()
    }

}