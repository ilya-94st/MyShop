package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject

class CheckLogin @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

  suspend operator fun invoke(etEmail :String, etPassword: String): EventClass? {
       val email = etEmail.trim { it <= ' ' }
       val password = etPassword.trim { it <= ' ' }
      return when(val result = CheckValid.validLoginDetails(etEmail, etPassword)) {
           is EventClass.ErrorIn -> {
               result
           }
           is EventClass.Success -> {
               authenticationRepository.logInUser(email, password)
           }
           else -> {
               result
           }
       }
   }

}