package com.example.myshop.presentation.ui.fragments.my_cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.Resource
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.response.CurrencyRates
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MyCartViewModel @Inject constructor(
    private val getProductInCart: GetProductInCart,
    private val getAllPriceInCart: GetAllPriceInCart,
    private val deleteProductInCart: DeleteProductInCart,
    private val updateProductsInCart: UpdateProductsInCart,
    private val getProducts: GetProducts,
    private val getCurrencyFromApi: GetCurrencyFromApi
    ): ViewModel() {
    private val _itemsCurrency: MutableStateFlow<Resource<CurrencyRates>> = MutableStateFlow(Resource.Loading())

    val itemsCurrency: StateFlow<Resource<CurrencyRates>> = _itemsCurrency.asStateFlow()

    private var _products = MutableLiveData<MutableList<Products>>()

    var products: LiveData<MutableList<Products>> = _products

    private var _productsInCart = MutableLiveData<MutableList<ProductsInCart>>()

    var productsInCart: LiveData<MutableList<ProductsInCart>> = _productsInCart

    private var number = 1

    private var _quantity = MutableLiveData<Int>()

    var quantity: LiveData<Int> = _quantity

    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getProductInCart(idBuyer: String) = viewModelScope.launch {
        _productsInCart.postValue(getProductInCart.invoke(idBuyer))
    }

    fun getProduct(userId: String) = viewModelScope.launch {
        _products.postValue(getProducts.invoke(userId))
    }

    fun getAllPriceInCart(userId: String, quantity: Int) = viewModelScope.launch  {
       _allPrice.postValue(getAllPriceInCart.invoke(userId, quantity))
   }

    fun updateProductInCart(oldQuantity: Int, quantity: Int, idOrder: Long) = viewModelScope.launch {
        updateProductsInCart.invoke(oldQuantity, quantity, idOrder)
    }

    fun deleteProductInCart(idOrder: Long) {
        deleteProductInCart.invoke(idOrder)
    }

    fun plusQuantity() {
        _quantity.value = ++ number
    }

    fun minusQuantity() {
        _quantity.value = -- number
    }

    private fun getCurrency() = viewModelScope.launch {
        safeBreakingNewsCall()
    }

    private suspend fun safeBreakingNewsCall() {
        _itemsCurrency.value = Resource.Loading()
        try {
                val response = getCurrencyFromApi.invoke()
                _itemsCurrency.value = response

        } catch(t: Throwable) {
            when(t) {
                is IOException -> _itemsCurrency.value = Resource.Error("Network Failure")
                else -> _itemsCurrency.value = Resource.Error("Conversion Error")
            }
        }
    }

    init {
        _quantity.value = 1
        getCurrency()
    }

}