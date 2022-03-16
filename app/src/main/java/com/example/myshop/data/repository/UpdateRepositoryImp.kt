package com.example.myshop.data.repository

import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateRepositoryImp @Inject constructor(): UpdateRepository {

    override fun updateUserProfileData(userHashMap: HashMap<String, Any>) {
        FireStore().updateUserProfileData(userHashMap)
    }
}