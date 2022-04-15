package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject

class CheckRegistration @Inject constructor(private val authenticationRepository: AuthenticationRepository)  {



    suspend operator fun invoke( etEmailID: String, etPassword: String, etFirstName: String, etLastName: String, etConfirmPassword: String, checked: Boolean): EventClass? {
        val email = etEmailID.trim { it <= ' ' }
        val password = etPassword.trim { it <= ' ' }
        return when(val result = CheckValid.validRegistrationDetails(etEmailID = email, etPassword = password,
        etFirstName = etFirstName, etLastName = etLastName, etConfirm = etConfirmPassword, checked = checked
            )) {
            is EventClass.ErrorIn -> {
                result
            }
            is EventClass.Success -> {
                authenticationRepository.registration(etEmailID, etPassword, etFirstName, etLastName)
            }
            else -> {
                result
            }
        }
    }
}