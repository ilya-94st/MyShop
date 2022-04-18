package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject


class CheckForgotPassword @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

suspend operator fun invoke(etEmail: String): EventClass? {
    val email = etEmail.trim { it <= ' ' }
    return when(val result = CheckValid.validEmailDetails(email)) {
        is EventClass.ErrorIn -> {
            result
        }
        is EventClass.Success -> {
            var registerResult: EventClass? = null
            try {
                registerResult = EventClass.Success
                authenticationRepository.checkForgotPassword(etEmail)
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