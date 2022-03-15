package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import java.security.cert.Extension
import javax.inject.Inject

class DeleteProducts @Inject constructor(private val productsRepository: ProductsRepository) {

  suspend fun deleteProduct() {
       productsRepository.deleteProduct()
    }

   suspend fun deleteImage(fileExtension: String) {
       productsRepository.deleteImageProduct(fileExtension)
    }
}