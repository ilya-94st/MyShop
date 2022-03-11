package com.example.myshop.data.repository

import androidx.fragment.app.Fragment
import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.CheckProductsRepository
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment
import javax.inject.Inject

class CheckProductsRepositoryImp @Inject constructor(): CheckProductsRepository {
    override fun checkUsersDetails(fragment: Fragment) {
        FireStore().getUsersDetails(fragment)
    }

    override fun checkUserMobile(
        descriptionProductFragment: DescriptionProductFragment,
        usersId: String
    ) {
        FireStore().getUserMobile(descriptionProductFragment, usersId)
    }
}