package com.example.myshop.domain.use_case

import com.example.myshop.data.FireStore
import com.example.myshop.presentation.ui.fragments.SettingsFragment

class CheckSettings {

    fun checkUserDetails(settingsFragment: SettingsFragment) {
        FireStore().getUsersDetails(settingsFragment)
    }
}