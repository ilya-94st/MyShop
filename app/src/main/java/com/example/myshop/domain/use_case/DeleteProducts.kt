package com.example.myshop.domain.use_case

import com.example.myshop.data.FireStore
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class DeleteProducts {

    fun deleteProduct(productsFragment: ProductsFragment) {
        FireStore().deleteProducts(productsFragment)
    }

    fun deleteImage(productsFragment: ProductsFragment) {
        FireStore().deleteImage(productsFragment)
    }
}