package com.example.myshop.presentation.ui.fragments.user_profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.GetUserProfile
import com.example.myshop.domain.use_case.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val getUserProfile: GetUserProfile, private val loader: ImageLoader): ViewModel() {

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun updateProfileUserDetails(users: Users, etMobile: String, etFirstName: String, etLastName: String, rbMale: Boolean, mUserProfileImageURL: String) {
        _result.postValue(getUserProfile.invoke(users, etMobile, etFirstName, etLastName, rbMale, mUserProfileImageURL))
    }


    fun loadImageToFirestore(fileExtension: String, imageFileUri: Uri?, constantsImages: String) =
        loader.loadImageToFirestore(fileExtension, imageFileUri, constantsImages)

}