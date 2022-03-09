package com.example.myshop.presentation.ui.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.databinding.FragmentDescriptionProductBinding
import com.example.myshop.domain.models.Users
import com.example.myshop.domain.use_case.ImageLoader
import com.example.myshop.presentation.base.BaseFragment


class DescriptionProductFragment : BaseFragment<FragmentDescriptionProductBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDescriptionProductBinding::inflate

    private val args: DescriptionProductFragmentArgs by navArgs()
    private lateinit var imageLoader: ImageLoader
    private lateinit var  mUserDetails: Users
    private lateinit var checkDescriptionsProduct: CheckDescriptionsProduct

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLoader = ImageLoader()
        checkDescriptionsProduct = CheckDescriptionsProduct()
        descriptionProduct()
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun descriptionProduct() {
        val products = args.products
        imageLoader.glideLoadUserPicture(products.image, binding.ivProduct, requireContext())
        binding.tvTitle.text = products.title
        binding.tvDescriptions.text = products.description
        binding.tvPrice.text = "${products.price}"
    }

    @SuppressLint("SetTextI18n")
    fun userDetailsSuccessful(users: Users) {
        mUserDetails = users

        hideProgressDialog()

        binding.tvMobile.text = "${users.mobile}"
    }

    private fun getUserDetails() {
        showProgressDialog("please wait")
        checkDescriptionsProduct.checkUserDetails(this)
    }
}