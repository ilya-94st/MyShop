package com.example.myshop.presentation.ui.fragments.settings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.databinding.FragmentSettingsBinding
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


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

        binding.tvAddress.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_selectAddressFragment)
        }

        binding.btLogout.setOnClickListener {
            // log out of the current account
            viewModel.logout()
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
                    glideLoadUserPicture(it.image, binding.ivUserPhoto, requireContext())
                    binding.tvName.text = "${it.firstName} ${it.lastName}"
                    binding.tvEmail.text = it.email
                    binding.tvGender.text = it.gender
                    binding.tvMobile.text = "${it.mobile}"
                }
    }

  private  fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        try {
            Glide.with(context).load(image)
                .centerCrop()
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}