package com.example.myshop.presentation.ui.fragments.checkout_orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CheckoutOrderViewModel @Inject constructor(
    private val getProductInCart: GetProductInCart,
    private val getAllPrice: GetAllPrice,
    private val checkUserDetails: CheckUserDetails,
    private val deleteAllProductsInCart: DeleteAllProductsInCart,
    private val addProductInOrder: AddProductInOrder
    ): ViewModel() {
    private var _idProduct = MutableLiveData<Long>()

    var idProduct: LiveData<Long> = _idProduct

    private var _time = MutableLiveData<String>()

    var time: LiveData<String> = _time

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _products = MutableLiveData<List<ProductsInCart>>()

    var products: LiveData<List<ProductsInCart>> = _products


    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getProductInCart(idBuyer: String) = viewModelScope.launch {
       _products.postValue(getProductInCart.invoke(idBuyer))
    }


    fun getAllPrice(userId: String) = viewModelScope.launch {
        _allPrice.postValue(getAllPrice.invoke(userId))
    }

    fun deleteProducts(idBuyer: String) {
        deleteAllProductsInCart.invoke(idBuyer)
    }

    fun addProductInOrder(productsInOrder: ProductsInOrder) = viewModelScope.launch {
        addProductInOrder.invoke(productsInOrder)
    }



    init {
        getUser()
        currentDate()
    }

    private fun getUser() = viewModelScope.launch {
        _users.postValue(checkUserDetails.invoke())
    }

 private  fun currentDate(){
        val calender = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
         _time.value = dateFormat.format(calender.time)
    }
}