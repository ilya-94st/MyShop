package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.AddAddressUser
import com.example.myshop.domain.use_case.CheckUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(private val addAddressUser: AddAddressUser, private val getUserDetails: CheckUserDetails): ViewModel() {
    private val _result = MutableLiveData<EventClass>()

    val result: LiveData<EventClass> = _result

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users> = _user

    fun addAddressUser(addressUser: AddressUser,etName: String, etPhoneNumber: String, etAddress: String, etZipCode: String, etNotes: String) = viewModelScope.launch {
        _result.postValue(addAddressUser.invoke(addressUser, etName,etPhoneNumber, etAddress, etZipCode, etNotes))
    }

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }
}