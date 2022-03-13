package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentForgotPasswordBinding
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {
    private val viewModel: ForgotPasswordViewModel by viewModels()


    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentForgotPasswordBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btSubmit.setOnClickListener {
            viewModel.validEmailDetails(binding.etEmail.text.toString())
        }

        viewModel.isLogged.observe(viewLifecycleOwner) {
            if(it == true) {
                hideProgressDialog()
                toast("You are logged successfully")
                findNavController().popBackStack()
            } else {
                showProgressDialog("Please wait...")
            }
        }

        viewModel.emailEvent.observe(viewLifecycleOwner){
                event->
            when(event) {
                is ForgotPasswordViewModel.ForgotPasswordInEvent.Success -> {
                    viewModel.checkSendPasswordResetEmail(binding.etEmail.text.toString())
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