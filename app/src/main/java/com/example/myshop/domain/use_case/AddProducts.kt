package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ProductsRepository
import javax.inject.Inject


class AddProducts @Inject constructor(private val productsRepository: ProductsRepository) {

   suspend operator fun invoke(products: Products): EventClass {
       return when(val result = CheckValid.validProduct(etTitle = products.title, etPrice = products.price.toString(), etDescription = products.description,
       etQuality = products.quantity.toString()
           )) {
           is EventClass.ErrorIn -> {
               result
           }
           else -> {
               productsRepository.addProducts(products)
               EventClass.Success
           }
       }
    }
}