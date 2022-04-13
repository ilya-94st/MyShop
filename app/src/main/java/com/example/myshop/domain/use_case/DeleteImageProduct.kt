package com.example.myshop.domain.use_case

import android.util.Log
import com.example.myshop.domain.repository.ProductsRepository
import java.io.IOException
import javax.inject.Inject

class DeleteImageProduct @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(idProducts: Long) {
        try {
        productsRepository.deleteImageProduct(idProducts)
    } catch (e: IOException) {
        Log.e("deleteImage", "$e")
    }
    }
}