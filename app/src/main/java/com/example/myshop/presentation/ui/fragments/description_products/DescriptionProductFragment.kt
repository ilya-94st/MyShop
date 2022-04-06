package com.example.myshop.presentation.ui.fragments.description_products
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.databinding.FragmentDescriptionProductBinding
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class DescriptionProductFragment : BaseFragment<FragmentDescriptionProductBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDescriptionProductBinding::inflate

    private val args: DescriptionProductFragmentArgs by navArgs()
    private val viewModel: DescriptionProductViewModel by viewModels()
    private var idBuyer = ""
    private var idOrders = 0L
    private var quantity = 0




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        descriptionProduct()
        viewModel.users.observe(viewLifecycleOwner){
            idBuyer = it.id
        }
        viewModel.idOrders.observe(viewLifecycleOwner){
            idOrders = it
        }

        binding.ibMinus.setOnClickListener {
            viewModel.minusQuantity()
        }

        binding.ibPlus.setOnClickListener {
            viewModel.plusQuantity()
        }

        binding.btAddToCart.setOnClickListener {
              addProductInCart()
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
        glideLoadUserPicture(products.image, binding.ivProduct, requireContext())
        viewModel.quantity.observe(viewLifecycleOwner) {
            binding.tvQuantity.text = "$it"
            quantity = it
        }
        binding.tvTitle.text = products.title
        binding.tvDescriptions.text = products.description
        binding.tvPrice.text = "${products.price} ${products.currency}"

    }

    private fun getUserMobile() {
        val products = args.products
        viewModel.getUserMobile(products.idSeller)
        binding.tvMobile.visibility = View.INVISIBLE
        showProgressDialog("please wait...")
        viewModel.usersMobile.observe(viewLifecycleOwner){
            binding.tvMobile.text =  "$it"
            hideProgressDialog()
            binding.tvMobile.visibility = View.VISIBLE
        }
    }

    private fun addProductInCart() {
        val product = args.products
        val idSeller = product.idSeller
        val idProducts = product.idProducts
        val imageProduct = product.image
        val price = product.price
        val title = product.title
        val currency = product.currency
        val quInProduct = product.quality
        if (quInProduct < quantity || quantity < 0) {
            errorSnackBar("not enough products", true)
        } else {
            val productInCart = ProductsInCart(idSeller, idProducts, idBuyer, idOrders, title, price, imageProduct, currency, quantity)
            val result = quInProduct - quantity
            viewModel.updateProducts(args.products, result)
            viewModel.addProductInCart(productInCart)
            findNavController().navigate(R.id.action_descriptionProductFragment_to_myCartFragment)
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