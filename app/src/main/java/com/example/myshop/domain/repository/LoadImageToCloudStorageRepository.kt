package com.example.myshop.domain.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask

interface LoadImageToCloudStorageRepository {

    fun upLoadImageToCloudStorage(userId: String, imageFileUri: Uri?, constantsImages: String): UploadTask
}