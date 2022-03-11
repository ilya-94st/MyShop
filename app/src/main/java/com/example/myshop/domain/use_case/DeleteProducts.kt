package com.example.myshop.domain.use_case

import com.example.myshop.data.repository.ProductsRepositoryImp
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class DeleteProducts(private val shopRepositoryImp: ProductsRepositoryImp) {

    fun deleteProduct(productsFragment: ProductsFragment) {
       shopRepositoryImp.deleteProduct(productsFragment)
    }

    fun deleteImage(productsFragment: ProductsFragment) {
       shopRepositoryImp.deleteImageProduct(productsFragment)
    }
}