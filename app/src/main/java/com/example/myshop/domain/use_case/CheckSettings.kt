package com.example.myshop.domain.use_case

import com.example.myshop.data.repository.ShopRepositoryImp
import com.example.myshop.presentation.ui.fragments.SettingsFragment

class CheckSettings(private val shopRepositoryImp: ShopRepositoryImp) {

    fun checkUserDetails(settingsFragment: SettingsFragment) {
        shopRepositoryImp.checkUserDetailsSettings(settingsFragment)
    }
}