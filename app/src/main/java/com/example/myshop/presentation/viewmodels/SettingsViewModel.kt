package com.example.myshop.presentation.viewmodels

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.ImageLoader
import com.example.myshop.presentation.ui.fragments.SettingsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val loader: ImageLoader, private val checkSettings: CheckUserDetails): ViewModel() {

    fun checkUserDetails(settingsFragment: SettingsFragment) {
        checkSettings.checkUserDetails(settingsFragment)
    }

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        loader.glideLoadUserPicture(image, imageView, context)
    }
}