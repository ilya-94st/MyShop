package com.example.myshop.presentation.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.GetUserProfile

class UserProfileViewModel(private val getUserProfile: GetUserProfile): ViewModel() {

    private val _mobilePhoneEvent: MutableLiveData<UserProfileInEvent> = MutableLiveData(UserProfileInEvent.Empty)

    val mobilePhoneEvent: LiveData<UserProfileInEvent> = _mobilePhoneEvent

    fun updateProfileUserDetails(fragment: Fragment, users: Users, etMobile: String, etFirstName: String, etLastName: String, rbMale: Boolean, mUserProfileImageURL: String) =
        getUserProfile.getProfileUserDetails(fragment, users, etMobile, etFirstName, etLastName, rbMale, mUserProfileImageURL)

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

    sealed class UserProfileInEvent {
        data class ErrorUserProfileInEvent(val error: String) : UserProfileInEvent()
        object Success : UserProfileInEvent()
        object Empty: UserProfileInEvent()
    }
}