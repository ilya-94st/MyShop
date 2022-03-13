package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject


class CheckForgotPassword @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun checkEmail(filed: String) = !filed.contains("@")

suspend fun checkSendPasswordResetEmail(etEmail: String) {
    authenticationRepository.checkForgotPassword(etEmail)
}
}