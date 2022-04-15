package com.example.myshop.presentation.ui.fragments.orders_details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.myshop.databinding.FragmentOrdersDetailsBinding
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class OrdersDetailsFragment : BaseFragment<FragmentOrdersDetailsBinding>() {
    private val args: OrdersDetailsFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrdersDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetailsOrder()
    }


    @SuppressLint("SetTextI18n")
    private fun getDetailsOrder() {
        showProgressDialog("Please wait...")
        val product = args.order

        binding.tvPrice.text = "${product.price} + ${product.currency}"
        glideLoadUserPicture(product.image, binding.ivProduct, requireContext())
        binding.tvTitle.text = product.title + product.idOrder
        binding.tvQuantity.text = "${product.quantity}"

        binding.tvOrderId.text = "${product.idOrder}"
        binding.tvOrderStatus.text = "wait"
        binding.tvDate.text = product.time

        binding.tvMyNote.text = product.notes
        binding.tvPhoneNumber.text = "${product.phoneNumber}"
        binding.tvMyAddressZip.text = product.address + "," + product.zipCode
        binding.tvFullName.text = product.name
        binding.tvNameAddress.text = product.chooseAddress


        binding.tvSubtotal.text = "Subtotal ${product.price}"
        binding.tvShippingCharge.text = "Shipping Charge 10$"
        binding.tvTotalAmount.text = "Total Amount ${product.price + 10F}"
        hideProgressDialog()

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