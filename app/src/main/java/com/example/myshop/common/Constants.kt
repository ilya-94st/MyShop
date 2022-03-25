package com.example.myshop.common

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object Constants {
    const val USERS = "users"
    const val PRODUCTS = "products"
    const val PRODUCT_IN_CART = "product_in_cart"
    const val ADDRESS_USER = "address_user"

    const val READ_STORAGE_PERMISSION_CODE = 2

    const val PICK_IMAGE_REQUEST_CODE = 1

    fun hasPhotoPermission(context: Context) = EasyPermissions.hasPermissions(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    const val MALE = "Male"
    const val FEMALE = "Female"

    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"

    const val MOBILE = "mobile"
    const val GENDER = "gender"
    const val IMAGE ="image"
    const val COMPLETE_PROFILE = "profileCompleted"

    const val USER_PROFILE_IMAGE = "User_Profile_Image"
    const val USER_PRODUCTS_IMAGES = "USER_Products_Images"

}