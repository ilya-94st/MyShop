package com.example.myshop.domain.repository

import android.net.Uri
import androidx.fragment.app.Fragment
import com.example.myshop.domain.models.Products
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment
import com.example.myshop.presentation.ui.fragments.SettingsFragment

interface ShopRepository {

    fun addProducts(fragment: AddProductsFragment, products: Products)

    fun checkUserDetailsAddProducts(addProductsFragment: AddProductsFragment)

    fun deleteProduct(productsFragment: ProductsFragment)

    fun deleteImage(productsFragment: ProductsFragment)

    fun checkUserMobile(descriptionProductFragment: DescriptionProductFragment, usersId: String)

    fun checkUserDetailsSettings(settingsFragment: SettingsFragment)

    fun getProduct(productsAdapter: ProductsAdapter, userId: String)

    fun getAllProducts(allProductsAdapter: AllProductsAdapter)

    fun checkUsersGetProducts(productsFragment: ProductsFragment)

    fun updateUserProfileData(fragment: Fragment, userHashMap: HashMap<String, Any>)

    fun upLoadImageToCloudStorage(fragment: Fragment, imageFileUri: Uri?, constantsImages: String)
}