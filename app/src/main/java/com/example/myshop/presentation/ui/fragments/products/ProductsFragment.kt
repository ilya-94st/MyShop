package com.example.myshop.presentation.ui.fragments.products

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
import com.example.myshop.databinding.FragmentProductsBinding
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
    private lateinit var productsAdapter: ProductsAdapter
    private val viewModel: ProductViewModel by viewModels()
    private var userId = ""
    private var idProducts = 0L


    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productsAdapter = ProductsAdapter()
        initAdapter()
        getIdProduct()
        deleteProductSwipe()
        deleteProduct()

        binding.ibAddProducts.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductsFragment)
        }

        binding.ibCart.setOnClickListener {
           findNavController().navigate(R.id.action_productsFragment_to_myCartFragment)
        }


    }


    private fun initAdapter() {
        binding.rvProducts.adapter = productsAdapter
        viewModel.users.observe(viewLifecycleOwner){
            userId = it.id
            viewModel.getProduct(userId)
        }
        viewModel.products.observe(viewLifecycleOwner){ products ->
            productsAdapter.submitList(products)
        }
    }

    private fun getIdProduct() {
       viewModel.products.observe(viewLifecycleOwner){ products->
           products.forEach {
               idProducts = it.idProducts
           }
       }
    }

    private fun alertDialogDeleteProduct(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                viewModel.deleteProduct(idProducts)
                //viewModel.deleteImage(userId)
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
        val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
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

    private fun deleteProduct() {
        productsAdapter.setOnItemClickListener {
            viewModel.deleteProduct(idProducts)
            //viewModel.deleteImage(userId)
        }
    }
}