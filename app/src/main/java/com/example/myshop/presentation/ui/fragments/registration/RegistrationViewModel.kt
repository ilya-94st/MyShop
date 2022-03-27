package com.example.myshop.presentation.ui.fragments.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
import com.example.myshop.domain.use_case.CheckRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val checkRegistration: CheckRegistration): ViewModel() {
    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun registrationUser(etEmailID: String, etPassword: String, etFirstName: String, etLastName: String, etConfirmPassword: String, checked: Boolean) = viewModelScope.launch {
        _result.postValue(checkRegistration.invoke(etEmailID = etEmailID, etPassword = etPassword,
            etFirstName = etFirstName, etLastName = etLastName, etConfirmPassword = etConfirmPassword, checked = checked))
    }

}