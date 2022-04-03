package com.example.myshop.presentation.ui.fragments.selected_address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.GetItemsAddress
import com.example.myshop.presentation.adapters.AddressAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectAddressViewModel @Inject constructor(private val getItemsAddress: GetItemsAddress, private val getUserDetails: CheckUserDetails): ViewModel() {
    private var _addressUser = MutableLiveData<List<AddressUser>>()

    var addressUser: LiveData<List<AddressUser>> = _addressUser

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users> = _user

    fun getItemsAddressUser(idUser: String) = viewModelScope.launch {
        _addressUser.postValue(getItemsAddress.invoke(idUser))
    }



    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }
}