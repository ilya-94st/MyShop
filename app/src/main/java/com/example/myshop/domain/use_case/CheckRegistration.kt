package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject

class CheckRegistration @Inject constructor(private val authenticationRepository: AuthenticationRepository)  {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun  passwordLength(field: String) = field.length <= 6

    fun isChecked(filed: Boolean) = !filed

    fun passwordAndConfirm(filedPassword: String, fieldConfirm: String) = filedPassword.trim{ it <= ' ' } != fieldConfirm.trim { it <= ' ' }

    suspend fun checkRegisterUser( etEmailID: String, etPassword: String, etFirstName: String, etLastName: String) {
        authenticationRepository.registration(etEmailID, etPassword, etFirstName, etLastName)
    }
}