package com.example.myshop.presentation.ui.fragments.add_products
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProducts: AddProducts, private val imageLoader: ImageLoader, private val checkUserDetails: CheckUserDetails) : ViewModel() {

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users

    private var _result = MutableLiveData<EventClass>()

    var result: LiveData<EventClass> = _result

    fun addProducts(products: Products, etTitle: String, etPrice: String, etDescription: String, etQuality: String) = viewModelScope.launch {
        _result.postValue(addProducts.invoke(products, etTitle = etTitle, etPrice = etPrice, etDescription = etDescription,
            etQuality = etQuality))
    }

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        imageLoader.glideLoadUserPicture(image, imageView, context)
    }

    fun loadImageToFirestore(userId: String, imageFileUri: Uri?, constantsImages: String) =
        imageLoader.loadImageToFirestore(userId, imageFileUri, constantsImages)

    private fun getUsers() = viewModelScope.launch {
       _users.postValue(checkUserDetails.invoke())
   }

    init {
        getUsers()
    }
}