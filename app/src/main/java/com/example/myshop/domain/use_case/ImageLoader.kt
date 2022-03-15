package com.example.myshop.domain.use_case

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myshop.domain.repository.UpdateRepository
import java.io.IOException
import javax.inject.Inject

class ImageLoader @Inject constructor(private val updateRepository: UpdateRepository) {

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        try {
            Glide.with(context).load(image)
                .centerCrop()
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadImageToFirestore(fileExtension: String, imageFileUri: Uri?, constantsImages: String) = updateRepository.upLoadImageToCloudStorage(fileExtension, imageFileUri, constantsImages)
}