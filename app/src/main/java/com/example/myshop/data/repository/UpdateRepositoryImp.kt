package com.example.myshop.data.repository

import com.example.myshop.common.EventClass
import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateRepositoryImp @Inject constructor(): UpdateRepository {

    override fun updateUserProfileData(userHashMap: HashMap<String, Any>): EventClass? {
        var registerResult: EventClass? = null

        try {
            registerResult = EventClass.Success
            FireStore().updateUserProfileData(userHashMap)
        } catch (e: Exception){
        registerResult =  EventClass.ErrorIn("${e.message}")
        }
    return registerResult
    }

    override fun upDataProducts(products: HashMap<String, Any>) {
        FireStore().updateProductsData(products)
    }
}