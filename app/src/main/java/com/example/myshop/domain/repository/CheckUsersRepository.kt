package com.example.myshop.domain.repository

import androidx.fragment.app.Fragment
import com.example.myshop.presentation.ui.fragments.DescriptionProductFragment

interface CheckUsersRepository {
    fun checkUsersDetails(fragment: Fragment)

   suspend fun checkUserMobile(usersId: String): Any?

}