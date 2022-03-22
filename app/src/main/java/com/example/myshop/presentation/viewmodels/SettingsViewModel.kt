package com.example.myshop.presentation.viewmodels

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckSettings
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val loader: ImageLoader, private val userDetails: CheckUserDetails, private val checkSettings: CheckSettings): ViewModel() {

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _isLoader = MutableLiveData<Boolean>()

    var isLoader: LiveData<Boolean> = _isLoader

   private fun getUserDetails() = viewModelScope.launch {
        _isLoader.postValue(false)
        _users.postValue(userDetails.invoke())
       _isLoader.postValue(true)
    }

    fun logout() {
        checkSettings.invoke()
    }


    init {
        getUserDetails()
    }

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        loader.glideLoadUserPicture(image, imageView, context)
    }
}