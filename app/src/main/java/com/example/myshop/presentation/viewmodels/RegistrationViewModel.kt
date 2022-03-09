package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.use_case.CheckRegistration
import com.example.myshop.presentation.ui.fragments.RegistrationFragment
import kotlinx.coroutines.launch

class RegistrationViewModel(private val checkRegistration: CheckRegistration): ViewModel() {
    private val _registrationEvent = MutableLiveData<RegistrationInEvent>(RegistrationInEvent.Empty)

    val registrationEvent: LiveData<RegistrationInEvent> = _registrationEvent

    fun registrationUser(etEmailID: String, etPassword: String, registrationFragment: RegistrationFragment, etFirstName: String, etLastName: String) = viewModelScope.launch {
        checkRegistration.checkRegisterUser(etEmailID, etPassword, registrationFragment, etFirstName, etLastName)
    }


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