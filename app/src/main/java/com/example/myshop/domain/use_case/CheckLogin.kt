package com.example.myshop.domain.use_case

import android.text.TextUtils

class CheckLogin {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun checkEmail(filed: String) = !filed.contains("@")

    fun  passwordLength(field: String) = field.length <= 6
}