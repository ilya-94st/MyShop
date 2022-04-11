package com.example.myshop.presentation.ui.fragments.add_address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.Constants
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
   private var _addressLocation = MutableLiveData<String>()

    var addressLocation: LiveData<String> = _addressLocation

    private var _idAddress = MutableLiveData<Long>()

    var idAddress: LiveData<Long> = _idAddress


    private val _result = MutableLiveData<EventClass>()

    val result: LiveData<EventClass> = _result

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users> = _user

    fun addAddressUser(addressUser: AddressUser,etName: String, etPhoneNumber: String, etAddress: String, etZipCode: String, etNotes: String) = viewModelScope.launch {
        _result.postValue(addAddressUser.invoke(addressUser, etName,etPhoneNumber, etAddress, etZipCode, etNotes))
    }

    private  fun getIdAddress() {
        _idAddress.value = (Math.random() * Constants.ID_ADDRESS_RANDOM).toLong()
    }

    init {
        getUser()
        getIdAddress()
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }
}