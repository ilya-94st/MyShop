package com.example.myshop.domain.use_case

import android.text.TextUtils

class CheckMobile {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

}