package com.example.myshop.data.repository

import android.net.Uri
import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.LoadImageToCloudStorageRepository
import javax.inject.Inject

class LoadImageToCloudStorageRepositoryImp @Inject constructor(): LoadImageToCloudStorageRepository {
    override suspend fun upLoadImageToCloudStorage(
        userId: String,
        imageFileUri: Uri?,
        constantsImages: String
    ) = FireStore().upLoadImageToCloudStorage(userId, imageFileUri, constantsImages)
}