package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentRegistrationBinding
import com.example.myshop.domain.use_case.CheckRegistration
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.RegistrationFactoryViewModel
import com.example.myshop.presentation.viewmodels.RegistrationViewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var viewModelFactory: RegistrationFactoryViewModel
    private lateinit var checkRegistration: CheckRegistration


    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRegistrationBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkRegistration = CheckRegistration()
        viewModelFactory = RegistrationFactoryViewModel(checkRegistration)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)


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

        viewModel.registrationEvent.observe(viewLifecycleOwner) {
                event->
            when(event) {
                is RegistrationViewModel.RegistrationInEvent.Success -> {
                    viewModel.registrationUser(binding.etEmailID.text.toString(), binding.etPassword.text.toString(), this@RegistrationFragment, binding.etFirstName.text.toString(),
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
            }
        }
    }
}
