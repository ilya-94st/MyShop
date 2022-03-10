package com.example.myshop.domain.use_case

import com.example.myshop.data.FireStore
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment

class CheckDescriptionsProduct {
    fun checkUserMobile(descriptionProductFragment: DescriptionProductFragment, usersId: String) {
        FireStore().getUserMobile(descriptionProductFragment, usersId)
    }
}