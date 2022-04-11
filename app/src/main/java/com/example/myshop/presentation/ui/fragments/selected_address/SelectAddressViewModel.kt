package com.example.myshop.presentation.ui.fragments.selected_address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.DeleteAddress
import com.example.myshop.domain.use_case.GetItemsAddress
import com.example.myshop.presentation.adapters.AddressAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectAddressViewModel @Inject constructor(
    private val getItemsAddress: GetItemsAddress,
    private val getUserDetails: CheckUserDetails,
    private val deleteAddress: DeleteAddress
    ): ViewModel() {
    private var _addressUser = MutableLiveData<MutableList<AddressUser>>()

    var addressUser: LiveData<MutableList<AddressUser>> = _addressUser

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users> = _user

    fun getItemsAddressUser(idUser: String) = viewModelScope.launch {
        _addressUser.postValue(getItemsAddress.invoke(idUser))
    }

    fun deleteAddress(idAddress: Long)  {
        deleteAddress.invoke(idAddress)
    }

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _user.postValue(getUserDetails.invoke())
    }
}