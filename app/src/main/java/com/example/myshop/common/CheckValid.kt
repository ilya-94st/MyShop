package com.example.myshop.common

import android.text.TextUtils

object CheckValid {

    private fun passwordAndConfirm(filedPassword: String, fieldConfirm: String) = filedPassword.trim{ it <= ' ' } != fieldConfirm.trim { it <= ' ' }

    private fun isChecked(filed: Boolean) = !filed

    private fun isEmptyField(filed:String) = TextUtils.isEmpty(filed.trim { it <= ' ' })

 //   private fun isFieldContainInValidDate(filed: String) = !filed.contains(".*[a-z].*".toRegex())

    private fun isEmptyFieldProduct(filed:String) = TextUtils.isEmpty(filed)

    private fun isEmptyFieldAddress(filed:String) = TextUtils.isEmpty(filed)

    private fun checkEmail(filed: String) = !filed.contains("@")

    private fun passwordLength(field: String) = field.length <= 6


    fun validProduct(etTitle: String, etPrice: String, etDescription: String, etQuality: String): EventClass {
        return when {
            isEmptyFieldProduct(etTitle) -> {
                EventClass.ErrorIn("enter filed product")
            }
            isEmptyFieldProduct(etDescription) -> {
                EventClass.ErrorIn("enter field description")
            }
            isEmptyFieldProduct(etPrice) -> {
                EventClass.ErrorIn("enter field price")
            }
            isEmptyFieldProduct(etQuality) -> {
                EventClass.ErrorIn("enter field qality")
            }
            else -> {
                EventClass.Success
            }
        }
    }

    fun validAddressUser(etName: String, etPhoneNumber: String, etAddress: String, etZipCode: String, etNotes: String): EventClass {
        return when {
            isEmptyFieldAddress(etName) -> {
                EventClass.ErrorIn("enter filed name")
            }
            isEmptyFieldAddress(etPhoneNumber) -> {
                EventClass.ErrorIn("enter field phone number")
            }
            isEmptyFieldAddress(etAddress) -> {
                EventClass.ErrorIn("enter field address")
            }
            isEmptyFieldAddress(etZipCode) -> {
                EventClass.ErrorIn("enter field zipCode")
            }
            isEmptyFieldAddress(etNotes) -> {
                EventClass.ErrorIn("enter field notes")
            }
            else -> {
                EventClass.Success
            }
        }
    }


     fun validLoginDetails(etEmail: String, etPassword: String): EventClass {
        return when {
            isEmptyField(etEmail) -> {
                EventClass.ErrorIn("enter email")
            }
            isEmptyField(etPassword) -> {
                EventClass.ErrorIn("enter password")
            }
            passwordLength(etPassword)-> {
                EventClass.ErrorIn("Введите password больше 6")
            }
            checkEmail(etEmail) -> {
                EventClass.ErrorIn("enter the correct email")
            }

            else -> {
                EventClass.Success
            }
        }
    }

    fun validRegistrationDetails(etFirstName: String, etLastName: String, etEmailID: String, etPassword: String, etConfirm: String, checked: Boolean): EventClass {
        return when {
            isEmptyField(etFirstName) -> {
                EventClass.ErrorIn("enter name")
            }
            isEmptyField(etLastName) -> {
                EventClass.ErrorIn("enter last name")
            }
            isEmptyField(etEmailID) -> {
                EventClass.ErrorIn("enter email")
            }
            checkEmail(etEmailID) -> {
                EventClass.ErrorIn("enter the correct email")
            }

            isEmptyField(etPassword) -> {
                EventClass.ErrorIn("enter password")
            }
            passwordLength(etPassword)-> {
                EventClass.ErrorIn("Введите password больше 6")
            }
            isEmptyField(etConfirm) -> {
                EventClass.ErrorIn("enter confirm")
            }
            passwordAndConfirm(etPassword, etConfirm) -> {
                EventClass.ErrorIn("не совпали пороли")
            }
            isChecked(checked) -> {
                EventClass.ErrorIn("enter agree")
            }

            else -> {
                EventClass.Success
            }
        }
    }

    fun validMobileDetails(etMobile: String): EventClass {
        return when {
      //      isFieldContainInValidDate(etMobile) -> {
     //          EventClass.ErrorIn("enter correct value")
    //        }
            isEmptyField(etMobile) -> {
                EventClass.ErrorIn("enter mobile number")
            }
            else -> {
                EventClass.Success
            }
        }
    }

    fun valid(): EventClass {
        return when {
            else -> {
                EventClass.Success
            }
        }
    }


    fun validEmailDetails(etEmail: String): EventClass {
        return when {
            isEmptyField(etEmail) -> {
                EventClass.ErrorIn("enter email")
            }

            checkEmail(etEmail) -> {
                EventClass.ErrorIn("enter the correct email")
            }
            else -> {
                EventClass.Success
            }
        }
    }
}