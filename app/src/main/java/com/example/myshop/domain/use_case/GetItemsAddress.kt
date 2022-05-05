package com.example.myshop.domain.use_case

import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.repository.AddressUserRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetItemsAddress @Inject constructor(private val addressUserRepository: AddressUserRepository) {

suspend operator fun invoke(idUser: String) : EventClass? {
    var registerResult: EventClass? = null

    try {
        addressUserRepository.getItemsAddress(idUser).addOnSuccessListener {
                task ->
            val listAddress: ArrayList<AddressUser> = arrayListOf()
            for (document in task) {
                val product = document.toObject<AddressUser>()
                listAddress.add(product)
            }
            registerResult = EventClass.GetAddressItems(listAddress)
        }.await()
    }catch (e: Exception) {
        registerResult =  EventClass.ErrorIn("${e.message}")
    }

    return registerResult
}

}