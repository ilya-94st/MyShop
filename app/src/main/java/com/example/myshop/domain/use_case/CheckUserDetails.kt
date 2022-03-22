package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.CheckUsersRepository
import javax.inject.Inject

class CheckUserDetails @Inject constructor(private val checkUsersRepository: CheckUsersRepository) {

    suspend operator fun invoke() = checkUsersRepository.getUserDetails()
}