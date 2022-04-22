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
import com.example.myshop.common.Constants
import com.example.myshop.databinding.FragmentDescriptionProductBinding
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.ui.prefs
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class DescriptionProductFragment : BaseFragment<FragmentDescriptionProductBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDescriptionProductBinding::inflate

    private val args: DescriptionProductFragmentArgs by navArgs()
    private val viewModel: DescriptionProductViewModel by viewModels()
    private var idOrders = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressDialog("please wait...")
        descriptionProduct()
        getUserMobile()

        viewModel.idOrders.observe(viewLifecycleOwner){
            idOrders = it
        }

        binding.btAddToCart.setOnClickListener {
              addProductInCart()
        }

        binding.ibLeft.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun descriptionProduct() {
        val products = args.products
        glideLoadUserPicture(products.image, binding.ivProduct, requireContext())
        binding.tvQuantity.text = "${products.quantity}"
        binding.tvTitle.text = products.title
        binding.tvDescriptions.text = products.description
        binding.tvPrice.text = "${products.price} ${products.currency}"
            if (prefs.idUser == products.idSeller) {
                binding.btAddToCart.visibility = View.INVISIBLE
                if (products.quantity == 0){
                    binding.btAddToCart.visibility = View.INVISIBLE
                    binding.tvErrorProduct.visibility = View.VISIBLE
                }
            }else {
                if (products.quantity == 0) {
                    binding.btAddToCart.visibility = View.INVISIBLE
                    binding.tvErrorProduct.visibility = View.VISIBLE
                }else {
                    binding.btAddToCart.visibility = View.VISIBLE
                }
            }

    }

    private fun getUserMobile() {
        val products = args.products
        viewModel.getUserMobile(products.idSeller)
        binding.tvMobile.visibility = View.INVISIBLE
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
        val productInCart = ProductsInCart(idSeller, idProducts, prefs.idUser, idOrders, title, price!!, imageProduct, currency, Constants.QUANTITIES)
            viewModel.addProductInCart(productInCart)
            findNavController().navigate(R.id.action_descriptionProductFragment_to_myCartFragment)
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