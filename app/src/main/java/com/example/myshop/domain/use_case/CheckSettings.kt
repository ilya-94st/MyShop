package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.CheckProductsRepository
import com.example.myshop.presentation.ui.fragments.SettingsFragment
import javax.inject.Inject

class CheckSettings @Inject constructor(private val checkProductsRepository: CheckProductsRepository) {

    fun checkUserDetails(settingsFragment: SettingsFragment) {
        checkProductsRepository.checkUserDetailsSettings(settingsFragment)
    }
}