package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject

class DeleteImageProduct @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(fileExtension: String) {
        productsRepository.deleteImageProduct(fileExtension)
    }
}