package com.example.myshop.presentation.ui.fragments.my_cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCartViewModel @Inject constructor(
    private val getProductInCart: GetProductInCart,
    private val checkUserDetails: CheckUserDetails,
    private val getAllPrice: GetAllPrice,
    private val updateProducts: UpdateProducts,
    private val deleteProductInCart: DeleteProductInCart
    ): ViewModel() {
    private var _productsInCart = MutableLiveData<List<ProductsInCart>>()

    var productsInCart: LiveData<List<ProductsInCart>> = _productsInCart

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getProductInCart(idBuyer: String) = viewModelScope.launch {
        _productsInCart.postValue(getProductInCart.invoke(idBuyer))
    }

    fun getAllPrice(userId: String) = viewModelScope.launch {
       _allPrice.postValue(getAllPrice.invoke(userId))
   }



    fun deleteProductInCart(idBuyer: String, idProduct: Long) {
        deleteProductInCart.invoke(idBuyer, idProduct)
    }

    init {
        getUser()
    }

   private fun getUser() = viewModelScope.launch {
       _users.postValue(checkUserDetails.invoke())
    }
}