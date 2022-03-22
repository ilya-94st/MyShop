package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject


class CheckForgotPassword @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

suspend operator fun invoke(etEmail: String): EventClass {
    return when(val result = CheckValid.validEmailDetails(etEmail)) {
        is EventClass.ErrorIn -> {
            result
        }
        else -> {
            authenticationRepository.checkForgotPassword(etEmail)
            EventClass.Success
        }
    }
}
}