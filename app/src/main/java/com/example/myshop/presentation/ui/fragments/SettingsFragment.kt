package com.example.myshop.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.data.FireStore
import com.example.myshop.databinding.FragmentSettingsBinding
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.GlideLoader
import com.example.myshop.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private lateinit var glideLoader: GlideLoader
    private lateinit var  mUserDetails: Users

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSettingsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glideLoader = GlideLoader()

        binding.tvEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("users", mUserDetails)
            }
            findNavController().navigate(R.id.action_settingsFragment_to_userProfileFragment, bundle)
        }

        binding.btLogout.setOnClickListener {
            // log out of the current account
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    private fun getUserDetails() {
        showProgressDialog("please wait")
        FireStore().getUsersDetails(this)
    }

    @SuppressLint("SetTextI18n")
    fun userDetailsSuccessful(users: Users) {
        mUserDetails = users

        hideProgressDialog()

        glideLoader.loadUserPicture(users.image, binding.ivUserPhoto, requireContext())
        binding.tvName.text = "${users.firstName} ${users.lastName}"
        binding.tvEmail.text = users.email
        binding.tvGender.text = users.gender
        binding.tvMobile.text = "${users.mobile}"
    }
}