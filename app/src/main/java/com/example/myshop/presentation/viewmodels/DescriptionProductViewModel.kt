package com.example.myshop.presentation.viewmodels

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.use_case.CheckDescriptionsProduct
import com.example.myshop.domain.use_case.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionProductViewModel @Inject constructor(private val loader: ImageLoader, private val checkDescriptionsProduct: CheckDescriptionsProduct): ViewModel() {

    private var _users = MutableLiveData<Any>()

    var users: LiveData<Any> = _users


    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        loader.glideLoadUserPicture(image, imageView, context)
    }

    fun getUserMobile(usersId: String) = viewModelScope.launch {
        _users.postValue(checkDescriptionsProduct.invoke(usersId))

    }
}