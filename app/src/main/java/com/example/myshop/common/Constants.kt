package com.example.myshop.common

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object Constants {

    const val QUANTITIES = 1
    const val USERS = "users"
    const val PRODUCTS = "products"
    const val PRODUCT_IN_CART = "product_in_cart"
    const val ADDRESS_USER = "address_user"
    const val PRODUCTS_IN_ORDERS = "products_in_orders"

    const val READ_STORAGE_PERMISSION_CODE = 2

    const val PICK_IMAGE_REQUEST_CODE = 1

    fun hasPhotoPermission(context: Context) = EasyPermissions.hasPermissions(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    const val BASE_URL = "https://www.nbrb.by"

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

    const val QUANTITIES_IN_PRODUCTS = "quality"
    const val QUANTITIES_IN_PRODUCTS_IN_CART = "quantity"

    const val ID_PRODUCTS_RANDOM = 100000000000000
    const val ID_ADDRESS_RANDOM = 100000000000000
}