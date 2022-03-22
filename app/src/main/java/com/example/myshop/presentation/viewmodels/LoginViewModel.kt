package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
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

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun validLoginDetails(etEmail :String, etPassword: String) = viewModelScope.launch {
       _result.postValue(checkLogin.invoke(etEmail, etPassword))
    }

    fun getUserDetails() = viewModelScope.launch {
        _users.postValue(checkUserDetails.invoke())
    }

}