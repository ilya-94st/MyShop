package com.example.myshop.presentation.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.databinding.FragmentAddProductsBinding
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.use_case.AddProducts
import com.example.myshop.domain.use_case.GlideLoader
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.ProductFactory
import com.example.myshop.presentation.viewmodels.ProductViewModel
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException


class AddProductsFragment : BaseFragment<FragmentAddProductsBinding>(), EasyPermissions.PermissionCallbacks {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAddProductsBinding::inflate

    private var mSelectedImageFileUri: Uri? = null
    private var mUserProductImageURL: String = ""
    private lateinit var glideLoader: GlideLoader
    private lateinit var addProducts: AddProducts
    private lateinit var  viewModel: ProductViewModel
    private lateinit var productFactory: ProductFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glideLoader = GlideLoader()
        addProducts = AddProducts()
        productFactory = ProductFactory(addProducts)
        viewModel = ViewModelProvider(this, productFactory).get(ProductViewModel::class.java)


        binding.ivGetPhoto.setOnClickListener {
            getPhotoPermission()
        }

        binding.btSubmit.setOnClickListener {
            viewModel.observeProduct(binding.etTitle.text.toString(), binding.etPrice.text.toString(),
            binding.etDescription.text.toString(), binding.etQuality.text.toString()
                )
        }

        viewModel.productEvent.observe(viewLifecycleOwner) { event->
            when(event) {
                is ProductViewModel.ProductInEvent.Success -> {
                    showProgressDialog("please wait ")
                    if (mSelectedImageFileUri != null) {
                        FireStore().upLoadImageToCloudStorage(this, mSelectedImageFileUri, Constants.USER_PRODUCTS_IMAGES)
                    } else {
                        val title = binding.etTitle.text.toString()
                        val price = binding.etPrice.text.toString()
                        val description = binding.etDescription.text.toString()
                        val quality = binding.etQuality.text.toString()
                        val products = Products(title = title, price = price.toFloat(), description = description, quality = quality.toInt(), image = mUserProductImageURL)
                        viewModel.addProducts(this, products)
                    }
                }
                is ProductViewModel.ProductInEvent.ErrorProductIn -> {
                    errorSnackBar(event.error, true)
                }
                else -> Unit
            }
        }
    }

    fun addProductsImageSuccessful(imageUrl: String) {
        hideProgressDialog()
        mUserProductImageURL = imageUrl
        val title = binding.etTitle.text.toString()
        val price = binding.etPrice.text.toString()
        val description = binding.etDescription.text.toString()
        val quality = binding.etQuality.text.toString()
        val products = Products(title = title, price = price.toFloat(), description = description, quality = quality.toInt(), image = mUserProductImageURL)
        viewModel.addProducts(this, products)
        findNavController().navigate(R.id.action_addProductsFragment_to_productsFragment)
    }

    private fun showImageChooser() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, Constants.PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        mSelectedImageFileUri = data.data!!

                        glideLoader.loadUserPicture(mSelectedImageFileUri!!, binding.ivPhoto, requireContext())

                    } catch (e: IOException) {
                        toast("image selected failed")
                    }
                }
            }
        } else if( requestCode == AppCompatActivity.RESULT_CANCELED) {
            toast("image result canceled")
        }
    }

    private fun getPhotoPermission() {
        EasyPermissions.requestPermissions(
            this,
            "you need accept location permission to use this app",
            Constants.READ_STORAGE_PERMISSION_CODE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(Constants.hasPhotoPermission(requireContext())){
            showImageChooser()
        }else{
            snackBar("No")
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this , perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }else {
            getPhotoPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults ,this)
    }
}