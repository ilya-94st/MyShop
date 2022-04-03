package com.example.myshop.domain.repository

import com.example.myshop.common.EventClass


interface UpdateRepository {

    fun updateUserProfileData(userHashMap: HashMap<String, Any>): EventClass?

}