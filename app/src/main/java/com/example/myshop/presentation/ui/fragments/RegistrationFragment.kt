package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.common.EventClass
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
            showProgressDialog("Please wait...")
            viewModel.registrationUser(
                etEmailID = binding.etEmailID.text.toString(), etPassword = binding.etPassword.text.toString(),
                etFirstName = binding.etFirstName.text.toString(), etLastName = binding.etLastName.text.toString(), etConfirmPassword = binding.etConfirm.text.toString(), checked = binding.checkBox.isChecked
            )
        }

        viewModel.result.observe(viewLifecycleOwner) {
                event->
            when(event) {
                is EventClass.Success -> {
                    hideProgressDialog()
                    toast("You are registered successfully")
                    findNavController().popBackStack()
                }
                is EventClass.ErrorIn -> {
                    hideProgressDialog()
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
