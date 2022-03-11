package com.example.myshop.domain.use_case

import android.text.TextUtils
import androidx.navigation.fragment.findNavController
import com.example.myshop.presentation.ui.fragments.ForgotPasswordFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await



class CheckForgotPassword {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun checkEmail(filed: String) = !filed.contains("@")


  suspend  fun checkSendPasswordResetEmail(fragment: ForgotPasswordFragment, etEmail: String) {
        fragment.showProgressDialog("Please wait ...")

        val email = etEmail.trim { it <= ' ' }
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        fragment.hideProgressDialog()

                        if (task.isSuccessful) {
                            fragment.errorSnackBar("You are logged successfully", false)
                            fragment.findNavController().popBackStack()
                        } else {
                            fragment.errorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }.await()
    }
}