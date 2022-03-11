package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.ui.fragments.ProductsFragment
import javax.inject.Inject

class DeleteProducts @Inject constructor(private val productsRepository: ProductsRepository) {

    fun deleteProduct(productsFragment: ProductsFragment) {
       productsRepository.deleteProduct(productsFragment)
    }

    fun deleteImage(productsFragment: ProductsFragment) {
       productsRepository.deleteImageProduct(productsFragment)
    }
}