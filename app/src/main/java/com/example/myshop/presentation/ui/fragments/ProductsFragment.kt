package com.example.myshop.presentation.ui.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.databinding.FragmentProductsBinding
import com.example.myshop.domain.models.Users
import com.example.myshop.presentation.adapters.ProductsAdapter
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
private lateinit var productsAdapter: ProductsAdapter
private lateinit var mUserDetails: Users
private var userId = ""
    private val viewModel: ProductViewModel by viewModels()



    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibAddProducts.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductsFragment)
        }
        initAdapter()
        userDetailsSuccessful()


        productsAdapter.setOnItemClickListener {
          // viewModel.deleteProduct()
           viewModel.deleteImage("jpg")
        }
    }

    @SuppressLint("SetTextI18n")
  private  fun userDetailsSuccessful() {
                viewModel.users.observe(viewLifecycleOwner){
                    mUserDetails = it
                    userId =  mUserDetails.id
                    viewModel.getProduct(productsAdapter, userId)
                }
    }

    private fun initAdapter() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.adapter = productsAdapter
    }

    private fun getFileExtension(fragment: Fragment, uri: Uri?): String? {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fragment.activity?.contentResolver?.getType(uri!!))
    }
}