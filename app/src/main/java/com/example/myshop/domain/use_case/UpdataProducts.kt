package com.example.myshop.domain.use_case

import com.example.myshop.common.Constants
import com.example.myshop.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdataProducts @Inject constructor(private val updataRepository: UpdateRepository) {

    operator fun invoke(id: String, title: String, price: Float, description: String, quality: Int, image: String, currency: String) {
        val productsHashMap = HashMap<String, Any>()

        productsHashMap[Constants.ID] = id
        productsHashMap[Constants.TITLE] = title
        productsHashMap[Constants.PRICE] = price
        productsHashMap[Constants.DESCRIPTION] = description
        productsHashMap[Constants.QUALITY] = quality
        productsHashMap[Constants.IMAGE] = image
        productsHashMap[Constants.CURRENCY] = currency
        updataRepository.upDataProducts(productsHashMap)
    }
}

