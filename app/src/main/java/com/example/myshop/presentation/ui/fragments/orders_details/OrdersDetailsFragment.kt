package com.example.myshop.presentation.ui.fragments.orders_details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.myshop.databinding.FragmentOrdersDetailsBinding
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class OrdersDetailsFragment : BaseFragment<FragmentOrdersDetailsBinding>() {
    private val viewModel: OrdersDetailsViewModel by viewModels()
    private val args: OrdersDetailsFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrdersDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }


    @SuppressLint("SetTextI18n")
    private fun initAdapter() {
        showProgressDialog("Please wait...")
        val product = args.order
        binding.tvPrice.text = "${product.price} + ${product.currency}"
        glideLoadUserPicture(product.image, binding.ivProduct, requireContext())
        binding.tvTitle.text = product.title + product.idOrder

        viewModel.user.observe(viewLifecycleOwner){
            viewModel.getItemsAddressUser(it.id)
        }

        viewModel.addressUser.observe(viewLifecycleOwner){
            it.forEach {
                address->
                binding.tvMyNote.text = address.notes
                binding.tvPhoneNumber.text = "${address.phoneNumber}"
                binding.tvMyAddressZip.text = address.address + "," + address.zipCode
                binding.tvFullName.text = address.name
                binding.tvNameAddress.text = address.chooseAddress
            }
            hideProgressDialog()
        }
            binding.tvSubtotal.text = "Subtotal ${product.price}"
            binding.tvShippingCharge.text = "Shipping Charge 10$"
            binding.tvTotalAmount.text = "Total Amount ${product.price + 10F}"

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