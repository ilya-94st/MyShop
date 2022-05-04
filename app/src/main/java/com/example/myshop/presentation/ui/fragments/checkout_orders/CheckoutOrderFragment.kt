package com.example.myshop.presentation.ui.fragments.checkout_orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCheckoutOrderBinding
import com.example.myshop.domain.models.ProductsInOrder
import com.example.myshop.presentation.adapters.ProductsAddInOrder
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.ui.prefs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class CheckoutOrderFragment : BaseFragment<FragmentCheckoutOrderBinding>() {
    private val savArgs: CheckoutOrderFragmentArgs by navArgs()
    private val viewModel: CheckoutOrderViewModel by viewModels()
    private lateinit var productsAddInOrder: ProductsAddInOrder
    private var time = ""
    private var quantityProductsInCart = 0
    private var quantityProduct = 0

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCheckoutOrderBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTime()
        getProducts()
        getQuantityProducts()
        getQuantityProductsInCart()
        initAdapter()
        getItemsAddress()
        getAllPrice()

        binding.btPlaceOder.setOnClickListener {
            if (quantityProduct< quantityProductsInCart || quantityProductsInCart < 0) {
                errorSnackBar("not enough products", true)
            } else {
                addProductsInOrder()
                updateProducts()
                deleteProductInCart()
                findNavController().navigate(R.id.action_checkoutOrderFragment_to_dashBoardFragment)
                toast("You order was placed successfully")
            }
        }
    }


    private fun getItemsAddress() {
        val address = savArgs.userAdres
        binding.tvMyNote.text = address.notes
        binding.tvPhoneNumber.text = "${address.phoneNumber}"
        binding.tvMyAddressZip.text = address.address + "," + address.zipCode
        binding.tvFullName.text = address.name
        binding.tvNameAddress.text = address.chooseAddress
    }

    private fun getAllPrice() {
        viewModel.getProductInCart(prefs.idUser)
        viewModel.productsInCart.observe(viewLifecycleOwner){ products->
            viewModel.getAllPriceInCart(products)
        }
        viewModel.allPrice.observe(viewLifecycleOwner){
            binding.tvSubtotal.text = "Subtotal $it"
            binding.tvShippingCharge.text = "Shipping Charge ${10}$"
            binding.tvTotalAmount.text = "Total Amount ${(it + 10F)}"
        }
    }

    private fun getProducts() {
        viewModel.productsInCart.observe(viewLifecycleOwner){ products->
            viewModel.getProduct(products)
        }
    }

    private fun deleteProductInCart() {
            viewModel.deleteProducts(prefs.idUser)
    }

    private fun getQuantityProducts() {
        viewModel.products.observe(viewLifecycleOwner){ listProducts ->
            listProducts.forEach {
                quantityProduct = it.quantity!!
            }
        }
    }

    private fun getTime() {
        viewModel.time.observe(viewLifecycleOwner){
            time = it
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initAdapter() {
        productsAddInOrder = ProductsAddInOrder()
        binding.vpProducts.adapter = productsAddInOrder

        viewModel.getProductInCart(prefs.idUser)
        viewModel.productsInCart.observe(viewLifecycleOwner){ products->
            productsAddInOrder.submitList(products)
        }
        TabLayoutMediator(binding.table, binding.vpProducts) {
                _, _ ->
        }.attach()
    }

    private fun getQuantityProductsInCart() {
        viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
            products.forEach {
                quantityProductsInCart = it.quantity
            }
        }
    }

    private fun updateProducts() {
        viewModel.products.observe(viewLifecycleOwner){ products ->
            products.forEach { product ->
                         val result = product.quantity?.minus(quantityProductsInCart)
                         if (result != null) {
                             viewModel.updateProducts(product, result, product.idProducts)
                         }
            }
        }
    }

    private fun addProductsInOrder() {
            viewModel.productsInCart.observe(viewLifecycleOwner){ productsList->
                productsList.forEach { product->
                    val addressItems = savArgs.userAdres
                    val notes = addressItems.notes
                    val phoneNumber = "${addressItems.phoneNumber}"
                    val nameAddress = addressItems.address
                    val addressZip = addressItems.zipCode
                    val fullName= addressItems.name
                    val chooseAddress = addressItems.chooseAddress
                    val productInOrder = ProductsInOrder(product.idSeller,
                        product.idBuyer,
                        product.idOrder,
                        product.title,
                        product.price,
                        product.image,
                        product.currency,
                        fullName,
                        nameAddress,
                        phoneNumber.toLong(),
                        addressZip,
                        notes,
                        chooseAddress,
                        time,
                        product.quantity
                    )
                    viewModel.addProductInOrder(productInOrder)
                }
            }
    }
}