package com.example.myshop.domain.use_case

import androidx.fragment.app.Fragment
import com.example.myshop.domain.repository.CheckProductsRepository
import javax.inject.Inject

class CheckUserDetails @Inject constructor(private val checkProductsRepository: CheckProductsRepository) {

    fun checkUserDetails(fragment: Fragment) {
        checkProductsRepository.checkUsersDetails(fragment)
    }
}