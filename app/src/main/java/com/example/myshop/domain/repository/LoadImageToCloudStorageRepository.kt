package com.example.myshop.domain.repository

import android.net.Uri

interface LoadImageToCloudStorageRepository {

  suspend fun upLoadImageToCloudStorage(userId: String, imageFileUri: Uri?, constantsImages: String): String
}