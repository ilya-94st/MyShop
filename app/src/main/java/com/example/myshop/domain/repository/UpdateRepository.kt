package com.example.myshop.domain.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask

interface UpdateRepository {

    fun updateUserProfileData(userHashMap: HashMap<String, Any>)

}