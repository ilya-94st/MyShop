package com.example.myshop.domain.repository

import androidx.fragment.app.Fragment
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment

interface CheckProductsRepository {
    fun checkUsersDetails(fragment: Fragment)

    fun checkUserMobile(descriptionProductFragment: DescriptionProductFragment, usersId: String)

}