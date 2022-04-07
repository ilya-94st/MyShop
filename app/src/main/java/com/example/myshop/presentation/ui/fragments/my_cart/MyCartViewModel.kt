package com.example.myshop.presentation.ui.fragments.my_cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
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
    private val deleteProductInCart: DeleteProductInCart,
    private val getProducts: GetProducts
    ): ViewModel() {
    private var _products = MutableLiveData<MutableList<Products>>()

    var products: LiveData<MutableList<Products>> = _products

    private var _productsInCart = MutableLiveData<List<ProductsInCart>>()

    var productsInCart: LiveData<List<ProductsInCart>> = _productsInCart

    private var number = 1

    private var _quantity = MutableLiveData<Int>()

    var quantity: LiveData<Int> = _quantity

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getProductInCart(idBuyer: String) = viewModelScope.launch {
        _productsInCart.postValue(getProductInCart.invoke(idBuyer))
    }

    fun getProduct(userId: String) = viewModelScope.launch {
        _products.postValue(getProducts.invoke(userId))
    }

    fun getAllPrice(userId: String) = viewModelScope.launch {
       _allPrice.postValue(getAllPrice.invoke(userId))
   }

    fun updateProducts(oldProduct: Products, quantity: Int) = viewModelScope.launch {
        updateProducts.invoke(oldProduct, quantity)
    }

    fun deleteProductInCart(idBuyer: String, idProduct: Long) {
        deleteProductInCart.invoke(idBuyer, idProduct)
    }

    fun plusQuantity() {
        _quantity.value = ++ number
    }

    fun minusQuantity() {
        _quantity.value = -- number
    }

    init {
        _quantity.value = 1
        getUser()

    }

   private fun getUser() = viewModelScope.launch {
       _users.postValue(checkUserDetails.invoke())
    }
}