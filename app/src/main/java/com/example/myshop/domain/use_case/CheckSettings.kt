package com.example.myshop.domain.use_case

import com.example.myshop.domain.repository.AuthenticationRepository
import javax.inject.Inject

class CheckSettings @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    operator fun invoke() {
        authenticationRepository.logout()
    }
}