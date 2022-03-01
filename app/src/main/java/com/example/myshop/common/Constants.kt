package com.example.myshop.common

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions

object Constants {
    const val USERS = "users"

    const val READ_STORAGE_PERMISSION_CODE = 2

    const val PICK_IMAGE_REQUEST_CODE = 1

    fun hasPhotoPermission(context: Context) = EasyPermissions.hasPermissions(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    const val MALE = "Male"
    const val FEMALE = "Female"

    const val MOBILE = "mobile"
    const val GENDER = "gender"

    const val USER_PROFILE_IMAGE = "User_Profile_Image"

    fun getFileExtension(fragment: Fragment, uri: Uri?): String? {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fragment.activity?.contentResolver?.getType(uri!!))
    }
}