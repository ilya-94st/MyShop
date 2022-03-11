package com.example.myshop.domain.repository

import com.example.myshop.presentation.ui.fragments.AddProductsFragment
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import com.example.myshop.presentation.ui.fragments.ProductsFragment
import com.example.myshop.presentation.ui.fragments.SettingsFragment

interface CheckProductsRepository {
    fun checkUsersGetProducts(productsFragment: ProductsFragment)

    fun checkUserMobile(descriptionProductFragment: DescriptionProductFragment, usersId: String)

    fun checkUserDetailsSettings(settingsFragment: SettingsFragment)

    fun checkUserDetailsAddProducts(addProductsFragment: AddProductsFragment)
}