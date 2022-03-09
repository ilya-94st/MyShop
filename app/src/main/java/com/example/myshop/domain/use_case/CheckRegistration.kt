package com.example.myshop.domain.use_case

import android.text.TextUtils
import androidx.navigation.fragment.findNavController
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Users
import com.example.myshop.presentation.ui.fragments.RegistrationFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CheckRegistration {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun  passwordLength(field: String) = field.length <= 6

    fun isChecked(filed: Boolean) = !filed

    fun passwordAndConfirm(filedPassword: String, fieldConfirm: String) = filedPassword.trim{ it <= ' ' } != fieldConfirm.trim { it <= ' ' }

    fun checkRegisterUser(etEmailID: String, etPassword: String, registrationFragment: RegistrationFragment, etFirstName: String, etLastName: String) {
        val email = etEmailID.trim { it <= ' ' }
        val password = etPassword.trim { it <= ' ' }

        registrationFragment.showProgressDialog("Please wait...")

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        //  hideProgressDialog()

                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = Users(
                                firebaseUser.uid,
                                etFirstName,
                                etLastName,
                                etEmailID,

                                )

                            FireStore().registerUser(registrationFragment, user)

                            //    FirebaseAuth.getInstance().signOut()
                            registrationFragment.findNavController().popBackStack()

                        } else {
                            registrationFragment.hideProgressDialog()
                            registrationFragment.errorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }.await()
            }
        }
    }
}