package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckLogin
import com.example.myshop.domain.use_case.CheckUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val checkLogin: CheckLogin, private val checkUserDetails: CheckUserDetails): ViewModel() {
    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users


    private var _isLogged = MutableLiveData<Boolean>()

    var isLogged: LiveData<Boolean> = _isLogged

    private val _loginEvent = MutableLiveData<LoginInEvent>(LoginInEvent.Empty)

    val loginEvent: LiveData<LoginInEvent> = _loginEvent

    fun getUserDetails() = viewModelScope.launch {
        _users.postValue(checkUserDetails.getUserDetails())
    }

    fun logInRegisterUser(etEmail :String, etPassword: String) =
        viewModelScope.launch {
            _isLogged.value = false
            checkLogin.logInRegisterUser(etEmail, etPassword)
            _isLogged.value = true
        }

    fun validLoginDetails(etEmail: String, etPassword: String): Boolean {
        return when {
            checkLogin.isEmptyField(etEmail) -> {
                _loginEvent.value = LoginInEvent.ErrorLoginIn("enter email")
                false
            }
            checkLogin.isEmptyField(etPassword) -> {
                _loginEvent.value = LoginInEvent.ErrorLoginIn("enter password")
                false
            }
            checkLogin.passwordLength(etPassword)-> {
                _loginEvent.value = LoginInEvent.ErrorLoginIn("Введите password больше 6")
                false
            }
            checkLogin.checkEmail(etEmail) -> {
                _loginEvent.value = LoginInEvent.ErrorLoginIn("enter the correct email")
                false
            }
            else -> {
                _loginEvent.value = LoginInEvent.Success
                true
            }
        }
    }

    sealed class LoginInEvent {
        data class ErrorLoginIn(val error: String) : LoginInEvent()
        object Success : LoginInEvent()
        object Empty: LoginInEvent()
    }

}