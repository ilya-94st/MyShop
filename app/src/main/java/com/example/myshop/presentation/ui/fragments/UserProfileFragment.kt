package com.example.myshop.presentation.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.common.Constants
import com.example.myshop.databinding.FragmentUserProfileBinding
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.viewmodels.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(), EasyPermissions.PermissionCallbacks  {
    private val args: UserProfileFragmentArgs by navArgs()
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""
    private val viewModel: UserProfileViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentUserProfileBinding::inflate

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeColorRadioGroup()
        getUsers()

        binding.ivUserPhoto.setOnClickListener {
            getPhotoPermission()
        }

        binding.btSave.setOnClickListener {
            viewModel.validMobile(binding.etMobile.text.toString())
        }

        viewModel.isUserProfileSuccessful.observe(viewLifecycleOwner){
            if(it == true) {
                userProfileUpdateSuccess()
            }
        }

        viewModel.mobilePhoneEvent.observe(viewLifecycleOwner) { event ->
            when(event) {
                is UserProfileViewModel.UserProfileInEvent.ErrorUserProfileInEvent -> {
                    errorSnackBar(event.error, true)
                    if (event.error == requireContext().getString(R.string.checkedMobile)) {
                        binding.etMobile.error = event.error
                    }
                }
                is UserProfileViewModel.UserProfileInEvent.Success -> {
                    showProgressDialog("please wait ")
                    if(mSelectedImageFileUri != null) {
                        viewModel.loadImageToFirestore(this, mSelectedImageFileUri, Constants.USER_PROFILE_IMAGE)
                    } else {
                       viewModel.updateProfileUserDetails(args.users, binding.etMobile.text.toString(),
                       binding.etFirstName.text.toString(), binding.etLastName.text.toString(), binding.rbMale.isChecked, mUserProfileImageURL
                           )
                    }
                }
                else -> Unit
            }
        }
    }

    fun imageUploadSuccess(imageURL: String) {
      //  hideProgressDialog()
       mUserProfileImageURL = imageURL
        viewModel.updateProfileUserDetails(args.users, binding.etMobile.text.toString(),
            binding.etFirstName.text.toString(), binding.etLastName.text.toString(), binding.rbMale.isChecked, mUserProfileImageURL
        )
    }

  private  fun userProfileUpdateSuccess() {
        hideProgressDialog()
        toast("success")
        findNavController().navigate(R.id.action_userProfileFragment_to_dashBoardFragment)
    }

    private fun getUsers() {
        val users = args.users

        binding.etFirstName.setText(users.firstName)
        binding.etLastName.setText(users.lastName)

        binding.etEmailID.isEnabled = false
        binding.etEmailID.setText(users.email)

        if(users.profileCompleted == 0) {
            binding.tvTitle.text = requireContext().getString(R.string.completed_profile)

            binding.etFirstName.isEnabled = false

            binding.etLastName.isEnabled = false


        } else {
            binding.tvTitle.text = requireContext().getString(R.string.exit_profile)
            viewModel.glideLoadUserPicture(users.image, binding.ivUserPhoto, requireContext())

            binding.etEmailID.isEnabled = false
            binding.etEmailID.setText(users.email)

            if(users.mobile != 0L) {
                binding.etMobile.setText(users.mobile.toString())
            }

            if(users.gender == Constants.MALE) {
                binding.rbMale.isChecked = true
            } else {
                binding.rbFemale.isChecked = true
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

                        viewModel.glideLoadUserPicture(mSelectedImageFileUri!!, binding.ivUserPhoto, requireContext())

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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeColorRadioGroup() {
        binding.rgGender.setOnCheckedChangeListener { _, checkedID->
            when(checkedID) {
                R.id.rb_male -> {
                    binding.rbFemale.background = resources.getDrawable(R.drawable.empty_oval_button)
                    binding.rbMale.background = resources.getDrawable(R.drawable.oval_button)
                }
                R.id.rb_female -> {
                    binding.rbFemale.background = resources.getDrawable(R.drawable.oval_button)
                    binding.rbMale.background = resources.getDrawable(R.drawable.empty_oval_button)
                }
            }
        }
    }
}