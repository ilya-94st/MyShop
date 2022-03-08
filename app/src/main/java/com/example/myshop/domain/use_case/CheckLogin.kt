package com.example.myshop.domain.use_case

import android.text.TextUtils
import com.example.myshop.data.FireStore
import com.example.myshop.presentation.ui.fragments.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CheckLogin {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun checkEmail(filed: String) = !filed.contains("@")

    fun  passwordLength(field: String) = field.length <= 6

    fun logInRegisterUser(fragment: LoginFragment, etEmail :String, etPassword: String) {
        val email = etEmail.trim { it <= ' ' }
        val password = etPassword.trim { it <= ' ' }

        fragment.showProgressDialog("Please wait ...")

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        //  hideProgressDialog()

                        if (task.isSuccessful) {
                            FireStore().getUsersDetails(fragment)
                        } else {
                            fragment.hideProgressDialog()
                            fragment.errorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }.await()
            }
        }
    }
}