package com.example.myshop.presentation.ui.fragments.add_address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.Constants
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.use_case.AddAddressUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val addAddressUser: AddAddressUser): ViewModel() {
   private var _addressLocation = MutableLiveData<String>()

    var addressLocation: LiveData<String> = _addressLocation

    private var _idAddress = MutableLiveData<Long>()

    var idAddress: LiveData<Long> = _idAddress


    private val _result = MutableLiveData<EventClass>()

    val result: LiveData<EventClass> = _result



    fun addAddressUser(addressUser: AddressUser,etName: String, etPhoneNumber: String, etAddress: String, etZipCode: String, etNotes: String) = viewModelScope.launch {
        _result.postValue(addAddressUser.invoke(addressUser, etName,etPhoneNumber, etAddress, etZipCode, etNotes))
    }

    private  fun getIdAddress() {
        _idAddress.value = (Math.random() * Constants.ID_ADDRESS_RANDOM).toLong()
    }

    init {
        getIdAddress()
    }

}