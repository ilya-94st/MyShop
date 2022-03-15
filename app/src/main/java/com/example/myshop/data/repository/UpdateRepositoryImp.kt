package com.example.myshop.data.repository

import android.net.Uri
import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.UpdateRepository
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class UpdateRepositoryImp @Inject constructor(): UpdateRepository {

    override fun updateUserProfileData(userHashMap: HashMap<String, Any>) {
        FireStore().updateUserProfileData(userHashMap)
    }

    override fun upLoadImageToCloudStorage(
        fileExtension: String,
        imageFileUri: Uri?,
        constantsImages: String
    ): UploadTask = FireStore().upLoadImageToCloudStorage(fileExtension, imageFileUri, constantsImages)
}