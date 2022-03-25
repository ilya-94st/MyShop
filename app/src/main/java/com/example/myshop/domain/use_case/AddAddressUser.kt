package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.repository.AddressUserRepository
import javax.inject.Inject

class AddAddressUser @Inject constructor(private val addressUserRepository: AddressUserRepository){

    suspend operator fun invoke(addressUser: AddressUser, etName: String, etPhoneNumber: String, etAddress: String, etZipCode: String, etNotes: String): EventClass {
        return when(val result = CheckValid.validAddressUser(etName, etPhoneNumber, etAddress, etZipCode, etNotes
        )) {
            is EventClass.ErrorIn -> {
                result
            }
            else -> {
                addressUserRepository.addAddressUser(addressUser)
                EventClass.Success
            }
        }
    }
}