package com.example.myshop.domain.repository


interface UpdateRepository {

    fun updateUserProfileData(userHashMap: HashMap<String, Any>)

}