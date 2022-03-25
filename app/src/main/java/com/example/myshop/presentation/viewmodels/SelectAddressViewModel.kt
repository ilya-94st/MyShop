package com.example.myshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.GetItemsAddress
import com.example.myshop.presentation.adapters.AddressAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectAddressViewModel @Inject constructor(private val getItemsAddress: GetItemsAddress, private val getUserDetails: CheckUserDetails): ViewModel() {

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users> = _user

    fun getItemsAddressUser(addressAdapter: AddressAdapter, idUser: String) {
        getItemsAddress.invoke(addressAdapter, idUser)
    }

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }
}