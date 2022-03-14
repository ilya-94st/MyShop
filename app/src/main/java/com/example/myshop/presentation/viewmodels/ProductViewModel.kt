package com.example.myshop.presentation.viewmodels
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.*
import com.example.myshop.presentation.adapters.ProductsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val addProducts: AddProducts, private val imageLoader: ImageLoader, private val deleteProducts: DeleteProducts,
private val getProducts: GetProducts, private val checkUserDetails: CheckUserDetails) : ViewModel() {

    private var _users = MutableLiveData<Users>()

    var users: LiveData<Users> = _users


    private val _productEvent = MutableLiveData<ProductInEvent>(ProductInEvent.Empty)

    val productEvent: LiveData<ProductInEvent> = _productEvent

    fun addProducts(products: Products) = viewModelScope.launch {
        addProducts.addProducts(products)
    }

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        imageLoader.glideLoadUserPicture(image, imageView, context)
    }

    fun loadImageToFirestore(fragment: Fragment, imageFileUri: Uri?, constantsImages: String) {
        imageLoader.loadImageToFirestore(fragment, imageFileUri, constantsImages)
    }

    private fun getUsers() = viewModelScope.launch {
       _users.postValue( checkUserDetails.getUserDetails())
   }

    init {
        getUsers()
    }

    fun deleteProduct() = viewModelScope.launch {
        deleteProducts.deleteProduct()
    }

    fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        getProducts.getProduct(productsAdapter, userId)
    }

    fun observeProduct(etTitle: String, etPrice: String, etDescription: String, etQuality: String): Boolean {
        return when {
            addProducts.isEmptyField(etTitle) -> {
                _productEvent.value = ProductInEvent.ErrorProductIn("enter filed product")
                false
            }
            addProducts.isEmptyField(etDescription) -> {
                _productEvent.value = ProductInEvent.ErrorProductIn("enter field description")
                false
            }
            addProducts.isEmptyField(etPrice) -> {
                _productEvent.value = ProductInEvent.ErrorProductIn("enter field price")
                false
            }
            addProducts.isEmptyField(etQuality) -> {
                _productEvent.value = ProductInEvent.ErrorProductIn("enter field qality")
                false
            }
            else -> {
                _productEvent.value = ProductInEvent.Success
                true
            }
        }
    }

    sealed class ProductInEvent {
        data class ErrorProductIn(val error: String) : ProductInEvent()
        object Success : ProductInEvent()
        object Empty: ProductInEvent()
    }
}