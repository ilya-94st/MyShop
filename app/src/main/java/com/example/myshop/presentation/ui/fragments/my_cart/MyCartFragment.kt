package com.example.myshop.presentation.ui.fragments.my_cart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentMyCartBinding
import com.example.myshop.presentation.adapters.ProductsInCartAdapter
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCartFragment : BaseFragment<FragmentMyCartBinding>() {
    private val viewModel: MyCartViewModel by viewModels()
    private lateinit var productsInCartAdapter: ProductsInCartAdapter
    private var quantity = 0
    private var userId = ""
    private var idProduct = 0L
    private var quantityProduct = 0


    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMyCartBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.quantity.observe(viewLifecycleOwner) {
            quantity = it
            binding.tvShippingPrice.text = "$quantity"
        }

        getAllPrice()
        initAdapter()
        getProducts()
        deleteProductSwipe()

        binding.btCheckout.setOnClickListener {
            if (quantityProduct < quantity || quantity < 0) {
                errorSnackBar("not enough products", true)
            } else {
                val result = quantityProduct - quantity
                viewModel.products.observe(viewLifecycleOwner){ products ->
                    products.forEach {
                        viewModel.updateProducts(it, result)
                    }
                }
                findNavController().navigate(R.id.action_myCartFragment_to_selectAddressFragment)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun initAdapter() {
        viewModel.users.observe(viewLifecycleOwner){
            viewModel.getProductInCart(it.id)
        }
        viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
            products.forEach {
                idProduct = it.idProduct
            }
            productsInCartAdapter = ProductsInCartAdapter(
                itemsQuantity = quantity
            )
            binding.rvProducts.adapter = productsInCartAdapter
            productsInCartAdapter.submitList(products)
            productsInCartAdapter.setOnItemClickListenerPlus {
                viewModel.plusQuantity()
            }
            productsInCartAdapter.setOnItemClickListenerMinus {
                viewModel.minusQuantity()
            }
            productsInCartAdapter.setOnItemClickListenerDelete {
                viewModel.deleteProductInCart(userId, idProduct)
            }
        }

    }

   private fun getProducts() {
       viewModel.users.observe(viewLifecycleOwner){
           viewModel.getProduct(it.id)
       }
       viewModel.products.observe(viewLifecycleOwner){ products ->
           products.forEach {
               quantityProduct = it.quality
           }
       }
   }

    private fun getAllPrice() {
        viewModel.getAllPrice(userId)
        viewModel.allPrice.observe(viewLifecycleOwner){
            binding.tvSubPrice.text = "$it"

            binding.tvTotalSum.text = "${(it + 10)}"
        }
    }

    private fun alertDialogDeleteProduct(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                viewModel.deleteProductInCart(userId, idProduct)
            }
            .setNegativeButton(
                "No"
            ) {
                    dialog, _ ->
                dialog.cancel()
                initAdapter()
            }
        builder.create().show()
    }

    private fun deleteProductSwipe() {
        val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
            , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            @SuppressLint("ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                alertDialogDeleteProduct("Do you want a delete product?")
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvProducts)
        }
    }
}