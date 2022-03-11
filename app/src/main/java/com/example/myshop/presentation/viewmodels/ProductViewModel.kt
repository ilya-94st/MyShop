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
import com.example.myshop.domain.use_case.AddProducts
import com.example.myshop.domain.use_case.DeleteProducts
import com.example.myshop.domain.use_case.GetProducts
import com.example.myshop.domain.use_case.ImageLoader
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val addProducts: AddProducts, private val imageLoader: ImageLoader, private val deleteProducts: DeleteProducts,
private val getProducts: GetProducts) : ViewModel() {

    private val _productEvent = MutableLiveData<ProductInEvent>(ProductInEvent.Empty)

    val productEvent: LiveData<ProductInEvent> = _productEvent

    fun addProducts(fragment: AddProductsFragment, products: Products) = viewModelScope.launch {
        addProducts.addProducts(fragment, products)
    }

    fun checkUserDetails(productsFragment: ProductsFragment) {
        getProducts.checkUserDetails(productsFragment)
    }

    fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        imageLoader.glideLoadUserPicture(image, imageView, context)
    }

    fun loadImageToFirestore(fragment: Fragment, imageFileUri: Uri?, constantsImages: String) {
        imageLoader.loadImageToFirestore(fragment, imageFileUri, constantsImages)
    }

    fun getUserId(addProductsFragment: AddProductsFragment) {
        addProducts.checkUserDetails(addProductsFragment)
    }

    fun deleteProduct(productsFragment: ProductsFragment) {
        deleteProducts.deleteProduct(productsFragment)
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