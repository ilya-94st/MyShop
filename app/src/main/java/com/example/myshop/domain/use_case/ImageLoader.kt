package com.example.myshop.domain.use_case

import android.net.Uri
import com.example.myshop.domain.repository.LoadImageToCloudStorageRepository
import javax.inject.Inject

class ImageLoader @Inject constructor(private val loadImageToCloudStorageRepository: LoadImageToCloudStorageRepository) {



   suspend operator fun invoke(userId: String, imageFileUri: Uri?, constantsImages: String) = loadImageToCloudStorageRepository.upLoadImageToCloudStorage(userId, imageFileUri, constantsImages)
}