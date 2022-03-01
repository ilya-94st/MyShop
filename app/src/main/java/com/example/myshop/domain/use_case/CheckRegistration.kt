package com.example.myshop.domain.use_case

import android.text.TextUtils

class CheckRegistration {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun  passwordLength(field: String) = field.length <= 6

    fun isChecked(filed: Boolean) = !filed

    fun passwordAndConfirm(filedPassword: String, fieldConfirm: String) = filedPassword.trim{ it <= ' ' } != fieldConfirm.trim { it <= ' ' }
}