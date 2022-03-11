package com.example.myshop.presentation.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.use_case.AddProducts
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import kotlinx.coroutines.launch

class ProductViewModel(private val addProducts: AddProducts) : ViewModel() {

    private val _productEvent = MutableLiveData<ProductInEvent>(ProductInEvent.Empty)

    val productEvent: LiveData<ProductInEvent> = _productEvent

    fun addProducts(fragment: AddProductsFragment, products: Products) = viewModelScope.launch {
        addProducts.addProducts(fragment, products)
    }


    fun getUserId(addProductsFragment: AddProductsFragment) {
        addProducts.checkUserDetails(addProductsFragment)
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