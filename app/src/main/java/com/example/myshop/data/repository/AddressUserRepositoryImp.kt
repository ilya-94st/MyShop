package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.repository.AddressUserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddressUserRepositoryImp @Inject constructor(private val fireStore: FirebaseFirestore): AddressUserRepository {

    override suspend fun addAddressUser(addressUser: AddressUser) {
            fireStore.collection(Constants.ADDRESS_USER).add(addressUser).await()
    }

    override  suspend  fun getItemsAddress(userId: String) = fireStore.collection(Constants.ADDRESS_USER).whereEqualTo("idUser", userId).get()

}