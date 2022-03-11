package com.example.myshop.presentation.ui.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.databinding.FragmentDescriptionProductBinding
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.DescriptionProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionProductFragment : BaseFragment<FragmentDescriptionProductBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDescriptionProductBinding::inflate

    private val args: DescriptionProductFragmentArgs by navArgs()
    private val viewModel: DescriptionProductViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        descriptionProduct()

        binding.ibLeft.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        getUserMobile()
    }

    @SuppressLint("SetTextI18n")
    private fun descriptionProduct() {
        val products = args.products
        viewModel.glideLoadUserPicture(products.image, binding.ivProduct, requireContext())
        binding.tvTitle.text = products.title
        binding.tvDescriptions.text = products.description
        binding.tvPrice.text = "${products.price}"
        binding.tvQuantity.text = "${products.quality}"
    }

    @SuppressLint("SetTextI18n")
    fun userMobileSuccessful(userMobile: Any) {
        binding.tvMobile.text = "$userMobile"
    }

    private fun getUserMobile() {
        val products = args.products
        viewModel.checkUserMobile(this, products.id)
    }
}