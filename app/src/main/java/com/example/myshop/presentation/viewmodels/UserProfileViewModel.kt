package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.domain.use_case.CheckMobile

class UserProfileViewModel(private val checkMobile: CheckMobile): ViewModel() {

    private val _mobilePhoneEvent: MutableLiveData<UserProfileInEvent> = MutableLiveData(UserProfileInEvent.Empty)

    val mobilePhoneEvent: LiveData<UserProfileInEvent> = _mobilePhoneEvent

    fun validMobile(etMobile: String): Boolean {
        return when {
            checkMobile.isEmptyField(etMobile) -> {
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