package com.example.myshop.domain.use_case

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myshop.domain.repository.LoadImageToCloudStorageRepository
import java.io.IOException
import javax.inject.Inject

class ImageLoader @Inject constructor(private val loadImageToCloudStorageRepository: LoadImageToCloudStorageRepository) {

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        try {
            Glide.with(context).load(image)
                .centerCrop()
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadImageToFirestore(userId: String, imageFileUri: Uri?, constantsImages: String) = loadImageToCloudStorageRepository.upLoadImageToCloudStorage(userId, imageFileUri, constantsImages)
}