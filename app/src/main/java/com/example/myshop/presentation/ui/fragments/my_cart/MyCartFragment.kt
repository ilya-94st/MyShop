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
import com.example.myshop.presentation.ui.prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class MyCartFragment : BaseFragment<FragmentMyCartBinding>() {
    private val viewModel: MyCartViewModel by viewModels()
    private lateinit var productsInCartAdapter: ProductsInCartAdapter
    private var quantity = 0
    private var userId = ""
    private var idProduct = 0L
    private var quantityProduct = 0

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMyCartBinding::inflate

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.users.observe(viewLifecycleOwner){
            userId = it.id
        }

        getAllPrice()
        initAdapter()
        getQuantity()
        getProducts()
        deleteProductSwipe()

        binding.btCheckout.setOnClickListener {
            if (quantityProduct < quantity || quantity < 0) {
                binding.tvErrorProduct.visibility = View.VISIBLE
                binding.tvErrorProduct.text = "not enough products"
            }else {
                viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
                    products.forEach {
                            product ->

                        viewModel.updateProductInCart(product, quantity)
                    }
                }
                prefs.preferences.edit().clear()
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
            products.forEach { product ->
                idProduct = product.idProduct
            }
            productsInCartAdapter = ProductsInCartAdapter(
                itemsQuantity = quantity,
                listProductsInCart = products
            )
            binding.rvProducts.adapter = productsInCartAdapter

            productsInCartAdapter.setOnItemClickListenerPlus {
                viewModel.plusQuantity()
                prefs.qunatity = quantity
            }
            productsInCartAdapter.setOnItemClickListenerMinus {
                viewModel.minusQuantity()
                prefs.qunatity = quantity
            }
            productsInCartAdapter.setOnItemClickListenerDelete {
                viewModel.deleteProductInCart(userId, idProduct)
            }
        }
    }

    private fun getQuantity() {
        viewModel.quantity.observe(viewLifecycleOwner) {
            quantity = it
        }
    }

   private fun getProducts() {
       viewModel.productsInCart.observe(viewLifecycleOwner){ products ->
           products.forEach {
               viewModel.getProduct(it.idSeller)
           }
       }
       viewModel.products.observe(viewLifecycleOwner){ products ->
           products.forEach {
               quantityProduct = it.quality
           }
       }
   }



    private fun getAllPrice() {
        viewModel.users.observe(viewLifecycleOwner){
            viewModel.getAllPrice(it.id)
        }
        viewModel.allPrice.observe(viewLifecycleOwner){
            binding.tvSubPrice.text = "$it"
            binding.tvShippingPrice.text = "10"
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

    @SuppressLint("CommitPrefEdits")
    override fun onDestroyView() {
        super.onDestroyView()
        prefs.preferences.edit().clear()
    }
}