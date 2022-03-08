package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.use_case.CheckForgotPassword
import com.example.myshop.presentation.ui.fragments.ForgotPasswordFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private  val checkForgotPassword: CheckForgotPassword): ViewModel() {

    private val _emailEvent = MutableStateFlow<ForgotPasswordInEvent>(ForgotPasswordInEvent.Empty)

    val emailEvent: StateFlow<ForgotPasswordInEvent> = _emailEvent

    fun checkSendPasswordResetEmail(fragment: ForgotPasswordFragment, etEmail: String)
    = viewModelScope.launch { checkForgotPassword.checkSendPasswordResetEmail(fragment, etEmail) }

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