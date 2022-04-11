package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.AddressUserRepository
import javax.inject.Inject

class GetItemsAddress @Inject constructor(private val addressUserRepository: AddressUserRepository) {

suspend operator fun invoke(idUser: String) = addressUserRepository.getItemsAddress(idUser)

}