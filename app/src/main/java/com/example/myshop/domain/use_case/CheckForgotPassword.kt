package com.example.myshop.domain.use_case

import android.text.TextUtils

class CheckForgotPassword {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun checkEmail(filed: String) = !filed.contains("@")

}