package com.example.myshop.domain.use_case

import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.example.myshop.common.Constants
import com.example.myshop.data.FireStore
import com.example.myshop.domain.models.Users

class GetUserProfile {

    fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

    fun getProfileUserDetails(fragment: Fragment, users: Users, etMobile: String, etFirstName: String, etLastName: String, rbMale: Boolean, mUserProfileImageURL: String) {

        val userHasMap = HashMap<String, Any>()

        val mobileNumber = etMobile.trim { it <= ' ' }

        val firstName = etFirstName.trim { it <= ' ' }
        if(firstName != users.firstName) {
            userHasMap[Constants.FIRST_NAME] = firstName
        }

        val lastName = etLastName.trim { it <= ' ' }
        if(lastName != users.lastName) {
            userHasMap[Constants.LAST_NAME] = lastName
        }

        val gender = if(rbMale) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        if (mobileNumber.isNotEmpty() && mobileNumber != users.mobile.toString()) {
            userHasMap[Constants.MOBILE] = mobileNumber.toLong()
        }

        if (gender.isNotEmpty() && gender != users.gender) {
            userHasMap[Constants.GENDER] = gender
        }

        if(mUserProfileImageURL.isNotEmpty()) {
            userHasMap[Constants.IMAGE] = mUserProfileImageURL
        }

        userHasMap[Constants.GENDER] = gender

        userHasMap[Constants.COMPLETE_PROFILE] = 1

        FireStore().updateUserProfileData(fragment, userHasMap)
    }
}