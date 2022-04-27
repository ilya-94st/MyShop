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
import com.example.myshop.domain.models.ProductsInCart
import com.example.myshop.presentation.adapters.ItemClickListener
import com.example.myshop.presentation.adapters.ProductsInCartAdapter
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.ui.prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class MyCartFragment : BaseFragment<FragmentMyCartBinding>(), ItemClickListener {
    private val viewModel: MyCartViewModel by viewModels()
    private lateinit var productsInCartAdapter: ProductsInCartAdapter
    private var quantityProduct = 0
    private var allPrice = 0F

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMyCartBinding::inflate

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner){
            errorSnackBar(it, true)
        }

        initAdapter()
        getAllPrice()
        getQuantityInProducts()

        binding.btCheckout.setOnClickListener {
            prefs.preferences.edit().clear()
            findNavController().navigate(R.id.action_myCartFragment_to_selectAddressFragment)
            //checkProductInCart()
        }
    }


  //  private fun checkProductInCart() {
 //       if (quantityProduct < quantity || quantity < 0) {
  //          binding.tvErrorProduct.visibility = View.VISIBLE
   //         binding.tvErrorProduct.text = "not enough products"
    //    }else {
     //       prefs.preferences.edit().clear()
      //      findNavController().navigate(R.id.action_myCartFragment_to_selectAddressFragment)
      //  }
   // }


    private fun initAdapter() {
        viewModel.getProductInCart(prefs.idUser)
        viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
            productsInCartAdapter = ProductsInCartAdapter(
                listProductsInCart = products,
                this
            )
            binding.rvProducts.adapter = productsInCartAdapter
            viewModel.getAllPriceInCart(products)
        }
    }

    private fun updateQuantity(idBuyer: String, idOrder: Long, quantity: Int) {
        viewModel.getQuantityInCart(idBuyer, idOrder)
        viewModel.quantityInCart.observe(viewLifecycleOwner){
            viewModel.updateProductInCart(it, quantity, idOrder)
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
        viewModel.allPrice.observe(viewLifecycleOwner) {
            allPrice = it
            binding.tvSubPrice.text = "$it"
            binding.tvShippingPrice.text = "10"
            binding.tvTotalSum.text = "${(it + 10)}"
       }
    }

    override fun minus(productsInCart: ProductsInCart, position: Int) {
        productsInCart.quantity -= 1
        updateQuantity(productsInCart.idBuyer, productsInCart.idOrder, productsInCart.quantity)
        viewModel.updateMinus(productsInCart.price, allPrice)
        productsInCartAdapter.notifyItemChanged(position)
    }

    override fun add(productsInCart: ProductsInCart, position: Int) {
        productsInCart.quantity += 1
        updateQuantity(productsInCart.idBuyer, productsInCart.idOrder, productsInCart.quantity)
        viewModel.updatePlus(productsInCart.price, allPrice)
        productsInCartAdapter.notifyItemChanged(position)
    }

    override fun deleteItem(productsInCart: ProductsInCart) {
        viewModel.deleteProductInCart(productsInCart.idOrder)
    }
}