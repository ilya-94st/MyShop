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

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        deleteProductSwipe()


        binding.ibAddProducts.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductsFragment)
        }

        binding.ibCart.setOnClickListener {
           findNavController().navigate(R.id.action_productsFragment_to_myCartFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        viewModel.users.observe(viewLifecycleOwner){
            viewModel.getProduct(it.id)
        }
        viewModel.products.observe(viewLifecycleOwner){ products ->
            productsAdapter = ProductsAdapter(products)
            binding.rvProducts.adapter = productsAdapter
            productsAdapter.setOnItemClickListener {
                viewModel.deleteProduct(it.idProducts)
            }
        }
    }

    private fun alertDialogDeleteProduct(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                viewModel.products.observe(viewLifecycleOwner){ products->
                    products.forEach {
                        viewModel.deleteProduct(it.idProducts)
                    }
                }
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

}