package com.example.myshop.data.repository

import android.net.Uri
import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.LoadImageToCloudStorageRepository
import javax.inject.Inject

class LoadImageToCloudStorageRepositoryImp @Inject constructor(): LoadImageToCloudStorageRepository {
    override fun upLoadImageToCloudStorage(
        fileExtension: String,
        imageFileUri: Uri?,
        constantsImages: String
    ) = FireStore().upLoadImageToCloudStorage(fileExtension, imageFileUri, constantsImages)
}