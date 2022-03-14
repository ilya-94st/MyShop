package com.example.myshop.data.repository

import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.CheckUsersRepository
import javax.inject.Inject

class CheckUsersRepositoryImp @Inject constructor(): CheckUsersRepository {

    override suspend fun checkUserMobile(usersId: String): Any? = FireStore().getUserMobile(usersId)


    override suspend fun getUserDetails() = FireStore().getUserDetails()


}
