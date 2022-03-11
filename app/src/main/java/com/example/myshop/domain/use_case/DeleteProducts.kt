package com.example.myshop.domain.use_case

import com.example.myshop.data.repository.ShopRepositoryImp
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class DeleteProducts(private val shopRepositoryImp: ShopRepositoryImp) {

    fun deleteProduct(productsFragment: ProductsFragment) {
       shopRepositoryImp.deleteProduct(productsFragment)
    }

    fun deleteImage(productsFragment: ProductsFragment) {
       shopRepositoryImp.deleteImage(productsFragment)
    }
}