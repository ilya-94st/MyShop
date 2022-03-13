package com.example.myshop.data.repository

import androidx.fragment.app.Fragment
import com.example.myshop.data.FireStore
import com.example.myshop.domain.repository.CheckUsersRepository
import javax.inject.Inject

class CheckUsersRepositoryImp @Inject constructor(): CheckUsersRepository {
    override fun checkUsersDetails(fragment: Fragment) {
        FireStore().getUsersDetails(fragment)
    }

    override suspend fun checkUserMobile(usersId: String): Any? = FireStore().getUserMobile(usersId)

}
