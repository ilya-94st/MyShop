package com.example.myshop.domain.use_case

import androidx.fragment.app.Fragment
import com.example.myshop.domain.repository.CheckUsersRepository
import javax.inject.Inject

class CheckUserDetails @Inject constructor(private val checkUsersRepository: CheckUsersRepository) {

    fun checkUserDetails(fragment: Fragment) {
        checkUsersRepository.checkUsersDetails(fragment)
    }
}