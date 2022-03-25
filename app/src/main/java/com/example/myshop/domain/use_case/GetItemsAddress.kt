package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.AddressUserRepository
import com.example.myshop.presentation.adapters.AddressAdapter
import javax.inject.Inject

class GetItemsAddress @Inject constructor(private val addressUserRepository: AddressUserRepository) {

   operator fun invoke(addressAdapter: AddressAdapter, idUser: String) {
       addressUserRepository.getItemsAddressUser(addressAdapter, idUser)
   }
}