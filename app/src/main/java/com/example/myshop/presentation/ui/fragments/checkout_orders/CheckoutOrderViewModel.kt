package com.example.myshop.presentation.ui.fragments.checkout_orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class CheckoutOrderViewModel @Inject constructor(
    private val getProductInCart: GetProductInCart,
    private val deleteAllProductsInCart: DeleteAllProductsInCart,
    private val addProductInOrder: AddProductInOrder,
    private val updateProducts: UpdateProducts,
    private val getProductIdProduct: GetProductIdProduct
    ): ViewModel() {
    private var _quantityInCart = MutableLiveData<List<Int>>()

    var quantityInCart: LiveData<List<Int>> = _quantityInCart

    private var _products = MutableLiveData<List<Products>>()

    var products: LiveData<List<Products>> = _products

    private var _time = MutableLiveData<String>()

    var time: LiveData<String> = _time

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    private var _allPrice = MutableLiveData<Float>()

    var allPrice: LiveData<Float> = _allPrice

    fun getProductInCart(idBuyer: String) = viewModelScope.launch {
       _result.postValue(getProductInCart.invoke(idBuyer))
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

    fun deleteProducts(idBuyer: String) {
        deleteAllProductsInCart.invoke(idBuyer)
    }

    fun addProductInOrder(productsInOrder: ProductsInOrder) = viewModelScope.launch {
        addProductInOrder.invoke(productsInOrder)
    }


    fun updateProducts(oldProduct: Products, quantity: Int, idProducts: Long){
        updateProducts.invoke(oldProduct, quantity, idProducts)
    }

    fun getQuantityProductsInCart(productsInCart: MutableList<ProductsInCart>) = viewModelScope.launch {
        val arrayList = ArrayList<Int>()
        for (product in productsInCart) {
                arrayList.add(product.quantity)
        }
        _quantityInCart.postValue(arrayList)
    }

    fun getProduct(productInCart: MutableList<ProductsInCart>) = viewModelScope.launch {
        val arrayListProducts = ArrayList<Products>()
        for (product in productInCart) {
            val idProduct = product.idProduct
          getProductIdProduct.invoke(idProduct).forEach {
              arrayListProducts.add(it)
          }
        }
        _products.postValue(arrayListProducts)
    }

    init {
        currentDate()
    }

 private  fun currentDate(){
        val calender = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
         _time.value = dateFormat.format(calender.time)
    }
}