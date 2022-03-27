package com.example.myshop.presentation.ui.fragments.description_products

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.AddProductsInCart
import com.example.myshop.domain.use_case.CheckDescriptionsProduct
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionProductViewModel @Inject constructor(private val getUserDetails: CheckUserDetails, private val loader: ImageLoader, private val checkDescriptionsProduct: CheckDescriptionsProduct, private val addProductsInCart: AddProductsInCart): ViewModel() {

    private var _usersMobile = MutableLiveData<Any>()

    var usersMobile: LiveData<Any> = _usersMobile

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        loader.glideLoadUserPicture(image, imageView, context)
    }

    fun getUserMobile(usersId: String) = viewModelScope.launch {
        _usersMobile.postValue(checkDescriptionsProduct.invoke(usersId))
    }

    fun addProductInCart(productsInCart: ProductsInCart) = viewModelScope.launch {
        addProductsInCart.invoke(productsInCart)
    }

   private fun getUsers() = viewModelScope.launch {
        _users.postValue(getUserDetails.invoke())
    }

    init {
        getUsers()
    }
}