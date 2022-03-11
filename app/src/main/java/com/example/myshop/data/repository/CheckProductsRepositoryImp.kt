package com.example.myshop.data.repository

import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.CheckProductsRepository
import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment
import com.example.myshop.presentation.ui.fragments.SettingsFragment

class CheckProductsRepositoryImp: CheckProductsRepository {

    override fun checkUsersGetProducts(productsFragment: ProductsFragment) {
        FireStore().getUsersDetails(productsFragment)
    }

    override fun checkUserDetailsSettings(settingsFragment: SettingsFragment) {
        FireStore().getUsersDetails(settingsFragment)
    }

    override fun checkUserMobile(
        descriptionProductFragment: DescriptionProductFragment,
        usersId: String
    ) {
        FireStore().getUserMobile(descriptionProductFragment, usersId)
    }

    override fun checkUserDetailsAddProducts(addProductsFragment: AddProductsFragment) {
        FireStore().getUsersDetails(addProductsFragment)
    }
}