package com.example.myshop.presentation.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
import com.example.myshop.domain.use_case.GetAllProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val getAllProducts: GetAllProducts): ViewModel() {

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun getAllProducts() = viewModelScope.launch {
      _result.postValue(getAllProducts.invoke())
    }

}