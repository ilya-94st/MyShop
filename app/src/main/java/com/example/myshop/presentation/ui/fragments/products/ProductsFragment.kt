package com.example.myshop.presentation.ui.fragments.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.common.EventClass
import com.example.myshop.databinding.FragmentProductsBinding
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.ui.prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
    private lateinit var productsAdapter: ProductsAdapter
    private val viewModel: ProductViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerProducts()

        binding.ibAddProducts.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductsFragment)
        }

        binding.ibCart.setOnClickListener {
           findNavController().navigate(R.id.action_productsFragment_to_myCartFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observerProducts() {
        viewModel.getProduct(prefs.idUser)
        viewModel.result.observe(viewLifecycleOwner){ event ->
            when (event) {
                is EventClass.GetProducts -> {
                    productsAdapter = ProductsAdapter(event.list)
                    binding.rvProducts.adapter = productsAdapter
                    productsAdapter.setOnItemClickListener {
                        viewModel.deleteProduct(it.idProducts)
                        viewModel.deleteImage(it.idProducts)
                    }
                }
                is EventClass.ErrorIn -> {
                    errorSnackBar(event.error, true)
                }
                else -> Unit
            }
        }
    }
}