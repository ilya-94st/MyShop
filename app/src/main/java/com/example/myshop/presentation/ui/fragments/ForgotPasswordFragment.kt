package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentForgotPasswordBinding
import com.example.myshop.domain.use_case.CheckForgotPassword
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.ForgotPasswordFactoryViewModel
import com.example.myshop.presentation.viewmodels.ForgotPasswordViewModel
import kotlinx.coroutines.flow.collect

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var factory: ForgotPasswordFactoryViewModel
    private lateinit var checkForgotPassword: CheckForgotPassword

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentForgotPasswordBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkForgotPassword = CheckForgotPassword()
        factory = ForgotPasswordFactoryViewModel(checkForgotPassword)
        viewModel = ViewModelProvider(this, factory).get(ForgotPasswordViewModel::class.java)

        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btSubmit.setOnClickListener {
            viewModel.validEmailDetails(binding.etEmail.text.toString())
        }

        lifecycleScope.launchWhenStarted {
            viewModel.emailEvent.collect {
                    event->
                when(event) {
                    is ForgotPasswordViewModel.ForgotPasswordInEvent.Success -> {
                        viewModel.checkSendPasswordResetEmail(this@ForgotPasswordFragment, binding.etEmail.text.toString())
                    }
                    is ForgotPasswordViewModel.ForgotPasswordInEvent.ErrorForgotPasswordIn -> {
                        errorSnackBar(event.error, true)
                        if(event.error == requireContext().getString(R.string.checkedEmail)) {
                            binding.etEmail.error = event.error
                        }
                        if(event.error == requireContext().getString(R.string.checkedEmailCorrect)) {
                            binding.etEmail.error = event.error
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}