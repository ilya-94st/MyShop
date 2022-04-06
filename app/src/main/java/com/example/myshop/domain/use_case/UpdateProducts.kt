package com.example.myshop.domain.use_case

import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateProducts @Inject constructor(private val updateRepository: UpdateRepository) {

 suspend operator fun invoke(oldProduct: Products, quantity: Int) {
       val productHashMap = HashMap<String, Any>()

       if (oldProduct.quality != quantity) {
           productHashMap["quality"] = quantity
       }


        updateRepository.upDataProducts(productHashMap, oldProduct)
    }
}

