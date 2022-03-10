package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment

class AddProducts {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed)

    fun addProducts(fragment: AddProductsFragment, products: Products) {
        FireStore().addProducts(fragment, products, Constants.PRODUCTS)
    }

    fun checkUserDetails(addProductsFragment: AddProductsFragment) {
        FireStore().getUsersDetails(addProductsFragment)
    }
}