package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.data.FireStore
import com.example.myshop.databinding.FragmentLoginBinding
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckLogin
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.LoginFactoryViewModel
import com.example.myshop.presentation.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginFactoryViewModel: LoginFactoryViewModel
    private lateinit var checkLogin: CheckLogin

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin = CheckLogin()
        loginFactoryViewModel = LoginFactoryViewModel(checkLogin)
        viewModel = ViewModelProvider(this, loginFactoryViewModel).get(LoginViewModel::class.java)

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.btLogin.setOnClickListener {
            viewModel.validLoginDetails(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.tvForgetPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        viewModel.loginEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is LoginViewModel.LoginInEvent.Success -> {
                    logInRegisterUser()
                }
                is LoginViewModel.LoginInEvent.ErrorLoginIn -> {
                    errorSnackBar(event.error, true)
                    if (event.error == requireContext().getString(R.string.checkedEmail)) {
                        binding.etEmail.error = event.error
                    }
                    if (event.error == requireContext().getString(R.string.checkedPassword)) {
                        binding.etPassword.error = event.error
                    }
                    if (event.error == requireContext().getString(R.string.checkedEmailCorrect)) {
                        binding.etEmail.error = event.error
                    }
                }
                else -> Unit
            }
        }
    }

    fun userLoggedInSuccessful(user: Users) {
        hideProgressDialog()
        Log.i("First Name", user.firstName)
        Log.i("Last Name", user.lastName)
        Log.i("Email", user.email)

        if(user.profileCommitted == 0) {
            // if user profile is incomplete then launch the UserProfileFragment
            val bundle = Bundle().apply {
                putSerializable("users", user)
            }
            findNavController().navigate(R.id.action_loginFragment_to_userProfileFragment, bundle)
        } else {
            // Redirect the user to Main screen after log in
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    private fun logInRegisterUser() {
        val email = binding.etEmail.text.toString().trim { it <= ' ' }
        val password = binding.etPassword.text.toString().trim { it <= ' ' }

        showProgressDialog("Please wait ...")

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        //  hideProgressDialog()

                        if (task.isSuccessful) {
                            FireStore().getUsersDetails(this@LoginFragment)
                        } else {
                            hideProgressDialog()
                            errorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }.await()
            }
        }
    }
}