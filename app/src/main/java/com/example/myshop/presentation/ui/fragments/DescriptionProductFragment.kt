package com.example.myshop.presentation.ui.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentDescriptionProductBinding
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.DescriptionProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DescriptionProductFragment : BaseFragment<FragmentDescriptionProductBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDescriptionProductBinding::inflate

    private val args: DescriptionProductFragmentArgs by navArgs()
    private val viewModel: DescriptionProductViewModel by viewModels()
    private var userId = ""



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        descriptionProduct()

        binding.btAddToCart.setOnClickListener {
            viewModel.users.observe(viewLifecycleOwner){
                userId = it.id
            }
                val product = args.products
                val imageProduct = product.image
                val price = product.price
                val title = product.title
                val currency = product.currency
                val productInCart = ProductsInCart(userId, title, price, imageProduct, currency)
                viewModel.addProductInCart(productInCart)
            findNavController().navigate(R.id.action_descriptionProductFragment_to_myCartFragment)
        }

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
        binding.tvPrice.text = "${products.price} ${products.currency}"
        binding.tvQuantity.text = "${products.quality}"
    }

    private fun getUserMobile() {
        val products = args.products
        viewModel.getUserMobile(products.id)
        binding.tvMobile.visibility = View.INVISIBLE
        showProgressDialog("please wait...")
        viewModel.usersMobile.observe(viewLifecycleOwner){
            binding.tvMobile.text =  "$it"
            hideProgressDialog()
            binding.tvMobile.visibility = View.VISIBLE
        }
    }
}