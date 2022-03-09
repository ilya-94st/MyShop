package com.example.myshop.presentation.ui.fragments

import com.example.myshop.data.FireStore

class CheckDescriptionsProduct {
    fun checkUserDetails(descriptionProductFragment: DescriptionProductFragment) {
        FireStore().getUsersDetails(descriptionProductFragment)
    }
}