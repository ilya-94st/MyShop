package com.example.myshop.presentation.ui.fragments.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.use_case.GetOrders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val getOrders: GetOrders): ViewModel() {
    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun getOrders(userId: String) = viewModelScope.launch {
        _result.postValue(getOrders.invoke(userId))
    }

}