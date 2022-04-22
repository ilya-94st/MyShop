package com.example.myshop.presentation.ui.fragments.my_cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentMyCartBinding
import com.example.myshop.presentation.adapters.ProductsInCartAdapter
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.ui.prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class MyCartFragment : BaseFragment<FragmentMyCartBinding>() {
    private val viewModel: MyCartViewModel by viewModels()
    private lateinit var productsInCartAdapter: ProductsInCartAdapter
    private var quantity = 0
    private var quantityProduct = 0

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMyCartBinding::inflate

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getQuantity()
        getAllPrice()
        initAdapter()
        getQuantityInProducts()

        binding.btCheckout.setOnClickListener {
            checkProductInCart()
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun checkProductInCart() {
        if (quantityProduct < quantity || quantity < 0) {
            binding.tvErrorProduct.visibility = View.VISIBLE
            binding.tvErrorProduct.text = "not enough products"
        }else {
            prefs.preferences.edit().clear()
            findNavController().navigate(R.id.action_myCartFragment_to_selectAddressFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initAdapter() {

            viewModel.getProductInCart(prefs.idUser)

        viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
            productsInCartAdapter = ProductsInCartAdapter(
                itemsQuantity = quantity,
                listProductsInCart = products
            )
            binding.rvProducts.adapter = productsInCartAdapter

            productsInCartAdapter.setOnItemClickListenerPlus {
                viewModel.plusQuantity()
                updateQuantity(it.idBuyer, it.idProduct)
                viewModel.getAllPriceInCart(it.idBuyer,quantity)
            }
            productsInCartAdapter.setOnItemClickListenerMinus {
                viewModel.minusQuantity()
                updateQuantity(it.idBuyer, it.idProduct)
                viewModel.getAllPriceInCart(it.idBuyer,quantity)
            }


            productsInCartAdapter.setOnItemClickListenerDelete { productInCart ->
                viewModel.deleteProductInCart(productInCart.idBuyer, productInCart.idProduct)
            }
        }
    }

    private fun updateQuantity(idBuyer: String, idProduct: Long) {
        viewModel.getProductInCart(idBuyer)
        viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
            products.forEach {
                    product ->
                viewModel.quantity.observe(viewLifecycleOwner){
                    viewModel.updateProductInCart(product.quantity, it, idProduct)
                    prefs.qunatity = it
                }
            }
        }
    }

    private fun getQuantity() {
        viewModel.quantity.observe(viewLifecycleOwner) {
            quantity = it
        }
    }

    private fun getQuantityInProducts() {
        viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
            products.forEach {
                viewModel.getProduct(it.idSeller)
            }
        }
        viewModel.products.observe(viewLifecycleOwner){ products ->
            products.forEach {
                quantityProduct = it.quantity!!
            }
        }
    }

    private fun getAllPrice() {
        viewModel.quantity.observe(viewLifecycleOwner) {
            viewModel.getAllPriceInCart(prefs.idUser, it)
        }
        viewModel.allPrice.observe(viewLifecycleOwner){
            binding.tvSubPrice.text = "$it"
            binding.tvShippingPrice.text = "10"
            binding.tvTotalSum.text = "${(it + 10)}"
        }
    }


    @SuppressLint("CommitPrefEdits")
    override fun onDestroyView() {
        super.onDestroyView()
        prefs.preferences.edit().clear()
    }
}