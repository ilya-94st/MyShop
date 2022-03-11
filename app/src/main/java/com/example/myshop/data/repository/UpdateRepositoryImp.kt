package com.example.myshop.data.repository

import android.net.Uri
import androidx.fragment.app.Fragment
import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.UpdateRepository

class UpdateRepositoryImp: UpdateRepository {

    override fun updateUserProfileData(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        FireStore().updateUserProfileData(fragment, userHashMap)
    }

    override fun upLoadImageToCloudStorage(
        fragment: Fragment,
        imageFileUri: Uri?,
        constantsImages: String
    ) {
        FireStore().upLoadImageToCloudStorage(fragment, imageFileUri, constantsImages)
    }
}