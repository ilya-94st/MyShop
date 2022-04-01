package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject

class CheckSettings @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    operator fun invoke() : EventClass? {
        return when(val result = CheckValid.valid()) {
            is EventClass.ErrorIn -> {
                result
            }
            is EventClass.Success -> {
                authenticationRepository.logout()
            }
            else -> {
                result
            }
        }
    }

}