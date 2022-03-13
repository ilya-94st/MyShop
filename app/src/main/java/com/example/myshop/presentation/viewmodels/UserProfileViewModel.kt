package com.example.myshop.presentation.viewmodels

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.GetUserProfile
import com.example.myshop.domain.use_case.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val getUserProfile: GetUserProfile, private val loader: ImageLoader): ViewModel() {

    private var _isUserProfileSuccessful = MutableLiveData<Boolean>()

    var isUserProfileSuccessful: LiveData<Boolean> = _isUserProfileSuccessful

    private val _mobilePhoneEvent: MutableLiveData<UserProfileInEvent> = MutableLiveData(UserProfileInEvent.Empty)

    val mobilePhoneEvent: LiveData<UserProfileInEvent> = _mobilePhoneEvent

    fun updateProfileUserDetails(users: Users, etMobile: String, etFirstName: String, etLastName: String, rbMale: Boolean, mUserProfileImageURL: String) {
        _isUserProfileSuccessful.value = false
        getUserProfile.getProfileUserDetails(users, etMobile, etFirstName, etLastName, rbMale, mUserProfileImageURL)
        _isUserProfileSuccessful.value = true
    }



    fun validMobile(etMobile: String): Boolean {
        return when {
            getUserProfile.isEmptyField(etMobile) -> {
                _mobilePhoneEvent.value = UserProfileInEvent.ErrorUserProfileInEvent("enter mobile number")
                false
            }
            else -> {
                _mobilePhoneEvent.value = UserProfileInEvent.Success
                true
            }
        }
    }

    fun loadImageToFirestore(fragment: Fragment, imageFileUri: Uri?, constantsImages: String) {
        loader.loadImageToFirestore(fragment, imageFileUri, constantsImages)
    }

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        loader.glideLoadUserPicture(image, imageView, context)
    }

    sealed class UserProfileInEvent {
        data class ErrorUserProfileInEvent(val error: String) : UserProfileInEvent()
        object Success : UserProfileInEvent()
        object Empty: UserProfileInEvent()
    }
}