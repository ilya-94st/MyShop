package com.example.myshop.data.repository

import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.repository.AddressUserRepository
import com.example.myshop.presentation.adapters.AddressAdapter
import javax.inject.Inject

class AddressUserRepositoryImp @Inject constructor(): AddressUserRepository {

    override suspend fun addAddressUser(addressUser: AddressUser) {
        FireStore().addAddressItems(addressUser)
    }

    override suspend fun getItemsAddressUser(userId: String) =
        FireStore().getItemsAddress(userId)

}