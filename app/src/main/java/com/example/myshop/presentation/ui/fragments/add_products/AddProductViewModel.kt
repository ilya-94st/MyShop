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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProducts: AddProducts,
    private val imageLoaderProducts: ImageLoaderProducts
    ) : ViewModel() {
    private var _mSelectedImageFileUri = MutableLiveData<Uri>()

    var mUserProfileImageURL: LiveData<Uri> = _mSelectedImageFileUri

    private var _idProducts = MutableLiveData<Long>()

    var idProducts: LiveData<Long> = _idProducts

    private var _image = MutableLiveData<String>()

    var image: LiveData<String> = _image

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun addProducts(products: Products) = viewModelScope.launch {
        _result.postValue(addProducts.invoke(products))
    }

    fun getUri(uri: Uri) {
        _mSelectedImageFileUri.value = uri
    }

    fun loadImageToFirestore(idProducts: Long, imageFileUri: Uri?, constantsImages: String) = viewModelScope.launch {
        _image.postValue(imageLoaderProducts.invoke(idProducts, imageFileUri, constantsImages))
    }

   private  fun getIdProducts() {
        _idProducts.value = (Math.random() * Constants.ID_PRODUCTS_RANDOM).toLong()
    }

    init {
        getIdProducts()
    }
}