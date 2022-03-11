package com.example.myshop.domain.use_case

import com.example.myshop.data.repository.ShopRepositoryImp
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment

class CheckDescriptionsProduct(private val shopRepositoryImp: ShopRepositoryImp) {
    fun checkUserMobile(descriptionProductFragment: DescriptionProductFragment, usersId: String) {
        shopRepositoryImp.checkUserMobile(descriptionProductFragment, usersId)
    }
}