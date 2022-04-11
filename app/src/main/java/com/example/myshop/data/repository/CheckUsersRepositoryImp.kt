package com.example.myshop.data.repository

import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.repository.CheckUsersRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CheckUsersRepositoryImp @Inject constructor(private val fireStore: FirebaseFirestore): CheckUsersRepository {

    override suspend fun checkUserMobile(usersId: String): Any? {
            val querySnapshot = fireStore.collection(Constants.USERS).whereEqualTo("id", usersId).get().await()

            return querySnapshot.documents[0].data?.get("mobile")

    }


    override suspend fun getUserDetails(): Users {
        val userDetails = fireStore.collection(Constants.USERS)
            .document(FireStore().getCurrentUserID())
            .get().await()

        return userDetails.toObject(Users::class.java)!!
    }


}
