package com.example.myshop.domain.repository


import com.example.myshop.domain.models.Users


interface CheckUsersRepository {
   suspend fun checkUserMobile(usersId: String): Any?

   suspend fun getUserDetails(): Users
}