package com.example.myshop.presentation.viewmodels

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.myshop.domain.use_case.CheckDescriptionsProduct
import com.example.myshop.domain.use_case.ImageLoader
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DescriptionProductViewModel @Inject constructor(private val loader: ImageLoader, private val checkDescriptionsProduct: CheckDescriptionsProduct): ViewModel() {

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        loader.glideLoadUserPicture(image, imageView, context)
    }

    fun checkUserMobile(descriptionProductFragment: DescriptionProductFragment, usersId: String) {
        checkDescriptionsProduct.checkUserMobile(descriptionProductFragment, usersId)
    }
}