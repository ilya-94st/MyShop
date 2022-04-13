package com.example.myshop.domain.use_case

import android.net.Uri
import com.example.myshop.domain.repository.LoadImageToCloudStorageRepository
import javax.inject.Inject

class ImageLoaderProducts @Inject constructor(private val loadImageToCloudStorageRepository: LoadImageToCloudStorageRepository) {

   suspend operator fun invoke(idProducts: Long, imageFileUri: Uri?, constantsImages: String) = loadImageToCloudStorageRepository.upLoadImageToCloudStorageProducts(idProducts, imageFileUri, constantsImages)
}