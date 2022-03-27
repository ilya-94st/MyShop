package com.example.myshop.presentation.ui.fragments.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
import com.example.myshop.domain.use_case.CheckForgotPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private  val checkForgotPassword: CheckForgotPassword): ViewModel() {

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result


    fun checkSendPasswordResetEmail(etEmail: String)
    = viewModelScope.launch {
        _result.postValue(checkForgotPassword.invoke(etEmail))
    }

}