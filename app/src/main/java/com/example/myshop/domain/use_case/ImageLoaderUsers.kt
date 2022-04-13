package com.example.myshop.domain.use_case

import android.net.Uri
import com.example.myshop.domain.repository.LoadImageToCloudStorageRepository
import javax.inject.Inject

class ImageLoaderUsers @Inject constructor(private val loadImageToCloudStorageRepository: LoadImageToCloudStorageRepository) {



   suspend operator fun invoke(userId: String, imageFileUri: Uri?, constantsImages: String) = loadImageToCloudStorageRepository.upLoadImageToCloudStorageUsers(userId, imageFileUri, constantsImages)
}