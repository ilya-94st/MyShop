package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class ProductsRepositoryImp: ProductsRepository {
    override fun addProducts(fragment: AddProductsFragment, products: Products) {
        FireStore().addProducts(fragment, products, Constants.PRODUCTS)
    }

    override fun deleteProduct(productsFragment: ProductsFragment) {
        FireStore().deleteProducts(productsFragment)
    }

    override fun deleteImageProduct(productsFragment: ProductsFragment) {
        FireStore().deleteImage(productsFragment)
    }

    override fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        FireStore().getProducts(productsAdapter, userId)
    }

    override fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        FireStore().getAllProducts(allProductsAdapter)
    }
}