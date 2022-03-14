package com.example.myshop.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentSettingsBinding
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private val viewModel: SettingsViewModel by viewModels()


    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSettingsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoader()
        userDetailsSuccessful()
        binding.tvEdit.setOnClickListener {
            val bundle = Bundle().apply {
                viewModel.users.observe(viewLifecycleOwner){
                    putSerializable("users", it)
                }
            }
            findNavController().navigate(R.id.action_settingsFragment_to_userProfileFragment, bundle)
        }

        binding.btLogout.setOnClickListener {
            // log out of the current account
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }

    private fun isLoader() {
        viewModel.isLoader.observe(viewLifecycleOwner){
            if(it == true) {
                hideProgressDialog()
            } else {
                showProgressDialog("Please wait...")
            }
        }
    }

    @SuppressLint("SetTextI18n")
   private fun userDetailsSuccessful() {
                viewModel.users.observe(viewLifecycleOwner){
                    viewModel.glideLoadUserPicture(it.image, binding.ivUserPhoto, requireContext())
                    binding.tvName.text = "${it.firstName} ${it.lastName}"
                    binding.tvEmail.text = it.email
                    binding.tvGender.text = it.gender
                    binding.tvMobile.text = "${it.mobile}"
                }
    }
}