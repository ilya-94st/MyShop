package com.example.myshop.domain.use_case

import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.example.myshop.common.Constants
import com.example.myshop.data.repository.ShopRepositoryImp
import com.example.myshop.domain.models.Users

class GetUserProfile(private val shopRepositoryImp: ShopRepositoryImp) {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun getProfileUserDetails(fragment: Fragment, users: Users, etMobile: String, etFirstName: String, etLastName: String, rbMale: Boolean, mUserProfileImageURL: String) {

        val userHashMap = HashMap<String, Any>()

        val mobileNumber = etMobile.trim { it <= ' ' }

        val firstName = etFirstName.trim { it <= ' ' }
        if(firstName != users.firstName) {
            userHashMap[Constants.FIRST_NAME] = firstName
        }

        val lastName = etLastName.trim { it <= ' ' }
        if(lastName != users.lastName) {
            userHashMap[Constants.LAST_NAME] = lastName
        }

        val gender = if(rbMale) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        if (mobileNumber.isNotEmpty() && mobileNumber != users.mobile.toString()) {
            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
        }

        if (gender.isNotEmpty() && gender != users.gender) {
            userHashMap[Constants.GENDER] = gender
        }

        if(mUserProfileImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }

        userHashMap[Constants.GENDER] = gender

        userHashMap[Constants.COMPLETE_PROFILE] = 1

        shopRepositoryImp.updateUserProfileData(fragment, userHashMap)
    }
}