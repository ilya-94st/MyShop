package com.example.myshop.domain.use_case

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader {

    fun loadUserPicture(image: Any, imageView: ImageView, context: Context) {
        try {
            Glide.with(context).load(image)
                .centerCrop()
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}