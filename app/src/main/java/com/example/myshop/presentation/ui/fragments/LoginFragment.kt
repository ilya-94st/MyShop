package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentLoginBinding
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.CheckLogin
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.LoginFactoryViewModel
import com.example.myshop.presentation.viewmodels.LoginViewModel


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
                    viewModel.logInRegisterUser(this@LoginFragment, binding.etEmail.text.toString(), binding.etPassword.text.toString())
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

        if(user.profileCompleted == 0) {
            // if user profile is incomplete then launch the UserProfileFragment
            val bundle = Bundle().apply {
                putSerializable("users", user)
            }
            findNavController().navigate(R.id.action_loginFragment_to_userProfileFragment, bundle)
        } else {
            // Redirect the user to Main screen after log in
            findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
        }
    }
}