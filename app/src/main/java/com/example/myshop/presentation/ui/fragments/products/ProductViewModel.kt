package com.example.myshop.presentation.ui.fragments.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.DeleteImageProduct
import com.example.myshop.domain.use_case.DeleteProducts
import com.example.myshop.domain.use_case.GetProducts
import com.example.myshop.presentation.adapters.ProductsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val deleteProducts: DeleteProducts, private val deleteImageProduct: DeleteImageProduct, private val getProducts: GetProducts, private val checkUserDetails: CheckUserDetails): ViewModel() {
    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    fun deleteProduct() = viewModelScope.launch {
        deleteProducts.invoke()
    }

    fun deleteImage(userId: String) = viewModelScope.launch {
        deleteImageProduct.invoke(userId)
    }

    fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        getProducts.invoke(productsAdapter, userId)
    }

    private fun getUsers() = viewModelScope.launch {
        _users.postValue(checkUserDetails.invoke())
    }

    init {
        getUsers()
    }
}