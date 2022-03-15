package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import java.security.cert.Extension
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(): ProductsRepository {
    override suspend fun addProducts(products: Products) {
        FireStore().addProducts(products, Constants.PRODUCTS)
    }

    override suspend fun deleteProduct() {
        FireStore().deleteProducts()
    }

    override suspend fun deleteImageProduct(fileExtension: String) {
        FireStore().deleteImage(fileExtension)
    }

    override fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        FireStore().getProducts(productsAdapter, userId)
    }

    override fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        FireStore().getAllProducts(allProductsAdapter)
    }
}