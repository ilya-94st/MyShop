package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.use_case.CheckForgotPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private  val checkForgotPassword: CheckForgotPassword): ViewModel() {
    private var _isLogged = MutableLiveData<Boolean>()

    var isLogged: LiveData<Boolean>  = _isLogged

    private val _emailEvent = MutableLiveData<ForgotPasswordInEvent>(ForgotPasswordInEvent.Empty)

    val emailEvent: LiveData<ForgotPasswordInEvent> = _emailEvent

    fun checkSendPasswordResetEmail(etEmail: String)
    = viewModelScope.launch {
        _isLogged.value = false
        checkForgotPassword.checkSendPasswordResetEmail(etEmail)
        _isLogged.value = true
    }

    fun validEmailDetails(etEmail: String): Boolean {
        return when {
            checkForgotPassword.isEmptyField(etEmail) -> {
                _emailEvent.value = ForgotPasswordInEvent.ErrorForgotPasswordIn("enter email")
                false
            }

            checkForgotPassword.checkEmail(etEmail) -> {
                _emailEvent.value = ForgotPasswordInEvent.ErrorForgotPasswordIn("enter the correct email")
                false
            }
            else -> {
                _emailEvent.value = ForgotPasswordInEvent.Success
                true
            }
        }
    }

    sealed class ForgotPasswordInEvent {
        data class ErrorForgotPasswordIn(val error: String) : ForgotPasswordInEvent()
        object Success : ForgotPasswordInEvent()
        object Empty: ForgotPasswordInEvent()
    }
}