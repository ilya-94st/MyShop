package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myshop.domain.use_case.CheckRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegistrationViewModel(private val checkRegistration: CheckRegistration): ViewModel() {
    private val _registrationEvent = MutableStateFlow<RegistrationInEvent>(RegistrationInEvent.Empty)

    val registrationEvent: StateFlow<RegistrationInEvent> = _registrationEvent


    fun validRegisterDetails(etFirstName: String, etLastName: String, etEmailID: String, etPassword: String, etConfirm: String, checked: Boolean): Boolean {
        return when {
            checkRegistration.isEmptyField(etFirstName) -> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("enter name")
                false
            }
            checkRegistration.isEmptyField(etLastName) -> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("enter last name")
                false
            }
            checkRegistration.isEmptyField(etEmailID) -> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("enter email")
                false
            }
            checkRegistration.isEmptyField(etPassword) -> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("enter password")
                false
            }
            checkRegistration.passwordLength(etPassword)-> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("Введите password больше 6")
                false
            }
            checkRegistration.isEmptyField(etConfirm) -> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("enter confirm")
                false
            }
            checkRegistration.passwordAndConfirm(etPassword, etConfirm) -> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("не совпали пороли")
                false
            }
            checkRegistration.isChecked(checked) -> {
                _registrationEvent.value = RegistrationInEvent.ErrorRegistrationIn("enter agree")
                false
            }
            else -> {
                _registrationEvent.value = RegistrationInEvent.Success
                true
            }
        }
    }

    sealed class RegistrationInEvent {
        data class ErrorRegistrationIn(val error: String) : RegistrationInEvent()
        object Success : RegistrationInEvent()
        object Empty: RegistrationInEvent()
    }
}