package com.example.myshop.presentation.ui.fragments.add_products
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.Constants
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.AddProducts
import com.example.myshop.domain.use_case.CheckUserDetails
import com.example.myshop.domain.use_case.ImageLoaderProducts
import com.example.myshop.domain.use_case.ImageLoaderUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProducts: AddProducts,
    private val imageLoaderProducts: ImageLoaderProducts,
    private val checkUserDetails: CheckUserDetails
    ) : ViewModel() {
    private var _idProducts = MutableLiveData<Long>()

    var idProducts: LiveData<Long> = _idProducts

    private var _image = MutableLiveData<String>()

    var image: LiveData<String> = _image

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun addProducts(products: Products, etTitle: String, etPrice: String, etDescription: String, etQuality: String) = viewModelScope.launch {
        _result.postValue(addProducts.invoke(products, etTitle = etTitle, etPrice = etPrice, etDescription = etDescription,
            etQuality = etQuality))
    }

    fun loadImageToFirestore(idProducts: Long, imageFileUri: Uri?, constantsImages: String) = viewModelScope.launch {
        _image.postValue(imageLoaderProducts.invoke(idProducts, imageFileUri, constantsImages))
    }


    private fun getUsers() = viewModelScope.launch {
       _users.postValue(checkUserDetails.invoke())
   }

   private  fun getIdProducts() {
        _idProducts.value = (Math.random() * Constants.ID_PRODUCTS_RANDOM).toLong()
    }
    init {
        getUsers()
        getIdProducts()
    }
}