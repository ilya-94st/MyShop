package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject


class CheckLogin @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun checkEmail(filed: String) = !filed.contains("@")

    fun  passwordLength(field: String) = field.length <= 6

   fun logInRegisterUser(etEmail :String, etPassword: String) =
        authenticationRepository.logInUser(etEmail, etPassword)


    fun checkUserLoginRegister(etEmail :String, etPassword: String) = authenticationRepository.checkUserRegister(etEmail, etPassword)

}