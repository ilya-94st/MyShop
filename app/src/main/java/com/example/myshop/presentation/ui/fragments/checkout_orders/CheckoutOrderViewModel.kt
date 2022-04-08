package com.example.myshop.presentation.ui.fragments.checkout_orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
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
    private val addProductInOrder: AddProductInOrder,
    private val updateProducts: UpdateProducts,
    private val getProducts: GetProducts,
    ): ViewModel() {
    private var _products = MutableLiveData<MutableList<Products>>()

    var products: LiveData<MutableList<Products>> = _products

    private var _idProduct = MutableLiveData<Long>()

    var idProduct: LiveData<Long> = _idProduct

    private var _time = MutableLiveData<String>()

    var time: LiveData<String> = _time

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _productsInCart = MutableLiveData<List<ProductsInCart>>()

    var productsInCart: LiveData<List<ProductsInCart>> = _productsInCart


    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getProductInCart(idBuyer: String) = viewModelScope.launch {
       _productsInCart.postValue(getProductInCart.invoke(idBuyer))
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

    fun updateProducts(oldProduct: Products, quantity: Int){
        updateProducts.invoke(oldProduct, quantity)
    }

    fun getProduct(userId: String) = viewModelScope.launch {
        _products.postValue(getProducts.invoke(userId))
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