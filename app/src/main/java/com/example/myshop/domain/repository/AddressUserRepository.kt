package com.example.myshop.domain.repository

import com.example.myshop.domain.models.AddressUser
import com.example.myshop.presentation.adapters.AddressAdapter

interface AddressUserRepository {

  suspend fun addAddressUser(addressUser: AddressUser)

  fun getItemsAddressUser(addressAdapter: AddressAdapter, userId: String)
}