package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentRegistrationBinding
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {
    private val viewModel: RegistrationViewModel by viewModels()



    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRegistrationBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.tvLogin.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivLeft.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btRegister.setOnClickListener {
            viewModel.validRegisterDetails(binding.etFirstName.text.toString(), binding.etLastName.text.toString(),
                binding.etEmailID.text.toString(), binding.etPassword.text.toString(), binding.etConfirm.text.toString(), binding.checkBox.isChecked
            )
        }

        viewModel.isUserCreate.observe(viewLifecycleOwner) {
               if(it == true) {
                   hideProgressDialog()
                   toast("You are registered successfully")
                   findNavController().popBackStack()
               } else {
                   showProgressDialog("Please wait...")
               }
        }

        viewModel.registrationEvent.observe(viewLifecycleOwner) {
                event->
            when(event) {
                is RegistrationViewModel.RegistrationInEvent.Success -> {
                    viewModel.registrationUser(binding.etEmailID.text.toString(), binding.etPassword.text.toString(), binding.etFirstName.text.toString(),
                        binding.etLastName.text.toString()
                    )
                }
                is RegistrationViewModel.RegistrationInEvent.ErrorRegistrationIn -> {
                    errorSnackBar(event.error, true)
                    if (event.error == requireContext().getString(R.string.checkedName)){
                        binding.etFirstName.error = event.error
                    }
                    if(event.error == requireContext().getString(R.string.checkedLatName)) {
                        binding.etLastName.error = event.error
                    }
                    if(event.error == requireContext().getString(R.string.checkedEmail)) {
                        binding.etEmailID.error = event.error
                    }
                    if(event.error == requireContext().getString(R.string.checkedPassword)) {
                        binding.etPassword.error = event.error
                    }
                    if(event.error == requireContext().getString(R.string.checkedConfirm)) {
                        binding.etConfirm.error = event.error
                    }
                }
                else -> Unit
            }
        }
    }
}
