package com.example.myshop.presentation.viewmodels

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.myshop.domain.use_case.CheckDescriptionsProduct
import com.example.myshop.domain.use_case.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DescriptionProductViewModel @Inject constructor(private val loader: ImageLoader, private val checkDescriptionsProduct: CheckDescriptionsProduct): ViewModel() {

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        loader.glideLoadUserPicture(image, imageView, context)
    }

    suspend fun getUserMobile(usersId: String): Any? {
       return checkDescriptionsProduct.checkUserMobile(usersId)
    }

}