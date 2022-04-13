package com.example.myshop.domain.repository

import android.net.Uri

interface LoadImageToCloudStorageRepository {

  suspend fun upLoadImageToCloudStorageUsers(userId: String, imageFileUri: Uri?, constantsImages: String): String

  suspend fun upLoadImageToCloudStorageProducts(idProducts: Long, imageFileUri: Uri?, constantsImages: String): String
}