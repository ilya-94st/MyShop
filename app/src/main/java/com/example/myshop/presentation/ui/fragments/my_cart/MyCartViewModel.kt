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
    private val deleteProductInCart: DeleteProductInCart,
    private val updateProductsInCart: UpdateProductsInCart,
    private val getProducts: GetProducts,
    private val getCurrencyFromApi: GetCurrencyFromApi,
    private val getQuantityInCart: GetQuantityInCart
    ): ViewModel() {

    private var _error = MutableLiveData<String>()

    var error: LiveData<String> = _error

    private var _quantityInCart = MutableLiveData<Int>()

    var quantityInCart: LiveData<Int> = _quantityInCart

    private val _itemsCurrency: MutableStateFlow<Resource<CurrencyRates>> = MutableStateFlow(Resource.Loading())

    val itemsCurrency: StateFlow<Resource<CurrencyRates>> = _itemsCurrency.asStateFlow()

    private var _products = MutableLiveData<MutableList<Products>>()

    var products: LiveData<MutableList<Products>> = _products

    private var _productsInCart = MutableLiveData<MutableList<ProductsInCart>>()

    var productsInCart: LiveData<MutableList<ProductsInCart>> = _productsInCart


    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getQuantityInCart(userId: String, idOrder: Long) = viewModelScope.launch {
            _quantityInCart.postValue(getQuantityInCart.invoke(userId, idOrder))
    }

    fun getProductInCart(idBuyer: String) = viewModelScope.launch {
        _productsInCart.postValue(getProductInCart.invoke(idBuyer))
    }

    fun getProduct(userId: String) = viewModelScope.launch {
        _products.postValue(getProducts.invoke(userId))
    }

    fun updatePlus(price: Float, priceSum: Float) {
         val upPrice = priceSum + price
        _allPrice.value = upPrice
    }

    fun updateMinus(price: Float, priceSum: Float) {
        val upPrice = priceSum - price
        _allPrice.value = upPrice
    }

    fun updateAllPriceAfterDelete(quantity: Int, allPrice: Float, price: Float) {
        val upPrice = allPrice - (price * quantity)
        _allPrice.value = upPrice
    }

    fun getAllPriceInCart(productInCart: MutableList<ProductsInCart>) {
        var priceAll = 0F
       for (product in productInCart) {
           val price = product.price
           val quantity = product.quantity
           priceAll += price * quantity
       }
        _allPrice.value = priceAll
   }

    fun updateProductInCart(oldQuantity: Int, quantity: Int, idOrder: Long) = viewModelScope.launch {
        updateProductsInCart.invoke(oldQuantity, quantity, idOrder)
    }

    fun deleteProductInCart(idOrder: Long) {
        deleteProductInCart.invoke(idOrder)
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
        getCurrency()
    }

}