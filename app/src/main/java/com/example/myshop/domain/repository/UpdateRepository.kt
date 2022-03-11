package com.example.myshop.domain.repository

import android.net.Uri
import androidx.fragment.app.Fragment

interface UpdateRepository {

    fun updateUserProfileData(fragment: Fragment, userHashMap: HashMap<String, Any>)

    fun upLoadImageToCloudStorage(fragment: Fragment, imageFileUri: Uri?, constantsImages: String)
}