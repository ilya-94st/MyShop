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
                var registerResult: EventClass? = null
                try {
                    registerResult = EventClass.Success
                   authenticationRepository.logout()
                } catch (e: Exception){
                    registerResult =  EventClass.ErrorIn("${e.message}")
                }
                return registerResult
            }
            else -> {
                result
            }
        }
    }

}