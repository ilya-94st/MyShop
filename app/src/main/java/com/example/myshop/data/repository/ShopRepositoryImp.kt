package com.example.myshop.data.repository

import android.net.Uri
import androidx.fragment.app.Fragment
import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.repository.ShopRepository
import com.example.myshop.presentation.adapters.AllProductsAdapter
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment
import com.example.myshop.presentation.ui.fragments.SettingsFragment

class ShopRepositoryImp: ShopRepository {
    override fun addProducts(fragment: AddProductsFragment, products: Products) {
        FireStore().addProducts(fragment, products, Constants.PRODUCTS)
    }

    override fun checkUserDetailsAddProducts(addProductsFragment: AddProductsFragment) {
        FireStore().getUsersDetails(addProductsFragment)
    }

    override fun deleteProduct(productsFragment: ProductsFragment) {
        FireStore().deleteProducts(productsFragment)
    }

    override fun deleteImage(productsFragment: ProductsFragment) {
        FireStore().deleteImage(productsFragment)
    }

    override fun checkUserMobile(
        descriptionProductFragment: DescriptionProductFragment,
        usersId: String
    ) {
        FireStore().getUserMobile(descriptionProductFragment, usersId)
    }

    override fun checkUserDetailsSettings(settingsFragment: SettingsFragment) {
        FireStore().getUsersDetails(settingsFragment)
    }

    override fun getProduct(productsAdapter: ProductsAdapter, userId: String) {
        FireStore().getProducts(productsAdapter, userId)
    }

    override fun getAllProducts(allProductsAdapter: AllProductsAdapter) {
        FireStore().getAllProducts(allProductsAdapter)
    }

    override fun checkUsersGetProducts(productsFragment: ProductsFragment) {
        FireStore().getUsersDetails(productsFragment)
    }

    override fun updateUserProfileData(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        FireStore().updateUserProfileData(fragment, userHashMap)
    }

    override fun upLoadImageToCloudStorage(
        fragment: Fragment,
        imageFileUri: Uri?,
        constantsImages: String
    ) {
        FireStore().upLoadImageToCloudStorage(fragment, imageFileUri, constantsImages)
    }

}