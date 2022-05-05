package com.example.myshop.domain.repository

import com.example.myshop.domain.models.AddressUser
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface AddressUserRepository {

  suspend fun addAddressUser(addressUser: AddressUser)

  suspend fun getItemsAddress(userId: String): Task<QuerySnapshot>
}