package com.example.myshop.presentation.ui.fragments.add_products

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.common.Constants
import com.example.myshop.common.EventClass
import com.example.myshop.databinding.FragmentAddProductsBinding
import com.example.myshop.domain.models.Products
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException

@AndroidEntryPoint
class AddProductsFragment : BaseFragment<FragmentAddProductsBinding>(), EasyPermissions.PermissionCallbacks {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAddProductsBinding::inflate

    private var mSelectedImageFileUri: Uri? = null
    private var mUserProductImageURL: String = ""
    private var currency: String = ""
    private var userId: String = ""
    private var idProducts: Long = 0L
    private  val  viewModelAdd: AddProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelAdd.users.observe(viewLifecycleOwner){
               userId = it.id
        }

        binding.ivGetPhoto.setOnClickListener {
            getPhotoPermission()
        }

        binding.spCurrency.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                when(position){
                    0 ->  {
                        currency = "USD"
                    }
                    1 -> {
                        currency = "EUR"
                    }
                    2 -> currency = "BYN"
                    3 -> currency = "RUB"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btSubmit.setOnClickListener {
            showProgressDialog("please wait ")
            loadImageToFireStore()
        }

        viewModelAdd.result.observe(viewLifecycleOwner) { event->
            when(event) {
                is EventClass.Success -> {
                    hideProgressDialog()
                    findNavController().navigate(R.id.action_addProductsFragment_to_productsFragment)
                }
                is EventClass.ErrorIn -> {
                    errorSnackBar(event.error, true)
                }
                else -> Unit
            }
        }
    }

   private fun observeUsers(imageUrl: String) {
       viewModelAdd.idProducts.observe(viewLifecycleOwner){
           idProducts = it
       }
       viewModelAdd.users.observe(viewLifecycleOwner){
           mUserProductImageURL = imageUrl
           val users = it
           val title = binding.etTitle.text.toString()
           val price = binding.etPrice.text.toString()
           val description = binding.etDescription.text.toString()
           val quality = binding.etQuantity.text.toString()
           val userId = users.id
           val products = Products(
               idSeller = userId,
               idProducts = idProducts,
               title = title,
               price = price.toFloat(),
               description = description,
               quality = quality.toInt(),
               image = mUserProductImageURL,
               currency = currency)
           viewModelAdd.addProducts(products, binding.etTitle.text.toString(), binding.etPrice.text.toString(),
               binding.etDescription.text.toString(), binding.etQuantity.text.toString())
       }
   }

    private fun loadImageToFireStore() {
        if (mSelectedImageFileUri != null) {
                viewModelAdd.loadImageToFirestore(userId, mSelectedImageFileUri, Constants.USER_PRODUCTS_IMAGES)
            viewModelAdd.image.observe(viewLifecycleOwner){
                observeUsers(it)
            }
        }
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

                        glideLoadUserPicture(mSelectedImageFileUri!!, binding.ivPhoto, requireContext())

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

  private  fun glideLoadUserPicture(image: Any, imageView: ImageView, context: Context) {
        try {
            Glide.with(context).load(image)
                .centerCrop()
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}