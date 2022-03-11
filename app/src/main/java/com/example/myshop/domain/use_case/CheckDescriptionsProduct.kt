package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.CheckProductsRepository
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import javax.inject.Inject

class CheckDescriptionsProduct @Inject constructor(private val checkProductsRepository: CheckProductsRepository) {
    fun checkUserMobile(descriptionProductFragment: DescriptionProductFragment, usersId: String) {
        checkProductsRepository.checkUserMobile(descriptionProductFragment, usersId)
    }
}