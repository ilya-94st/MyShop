package com.example.myshop.domain.use_case

import com.example.myshop.common.CheckValid
import com.example.myshop.common.EventClass
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CheckRegistration @Inject constructor(private val authenticationRepository: AuthenticationRepository)  {

    suspend operator fun invoke( etEmailID: String, etPassword: String, etFirstName: String, etLastName: String, etConfirmPassword: String, checked: Boolean): EventClass? {
        val email = etEmailID.trim { it <= ' ' }
        val password = etPassword.trim { it <= ' ' }
        return when(val result = CheckValid.validRegistrationDetails(etEmailID = email, etPassword = password,
        etFirstName = etFirstName, etLastName = etLastName, etConfirm = etConfirmPassword, checked = checked
            )) {
            is EventClass.ErrorIn -> {
                result
            }
            is EventClass.Success -> {
                var registerResult: EventClass? = null
                try {
                    authenticationRepository.registration(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                registerResult = EventClass.Success
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                val user = Users(
                                    firebaseUser.uid,
                                    etFirstName,
                                    etLastName,
                                    etEmailID,
                                )

                             authenticationRepository.registerUser(user)
                            } else {
                                EventClass.ErrorIn("${task.exception?.message}")
                            }
                        }.await()
                } catch (e: Exception) {
                    registerResult =  EventClass.ErrorIn("${e.message}")
                }
                return registerResult
            }
            else -> {
                result
            }
        }
    }
}