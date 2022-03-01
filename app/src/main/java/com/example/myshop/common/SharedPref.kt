package com.example.myshop.common

import android.content.Context
import android.content.SharedPreferences

class SharedPref(private val context: Context) {

    private val KEY_LOGIN = "login"

    private val KEY_PASSWORD = "password"

    private val KEY_SAVE = "save"


    private val KEY_NAME = "name"

    val preferences: SharedPreferences = context.getSharedPreferences("Shared", Context.MODE_PRIVATE)

    var login: String
        get() = preferences.getString(KEY_LOGIN, "")!!
        set(value) = preferences.edit().putString(KEY_LOGIN, value).apply()

    var name: String
        get() = preferences.getString(KEY_NAME, "")!!
        set(value) = preferences.edit().putString(KEY_NAME, value).apply()

    var pasword: String
        get() = preferences.getString(KEY_PASSWORD, "")!!
        set(value) = preferences.edit().putString(KEY_PASSWORD, value).apply()

    var save: Boolean
        get() = preferences.getBoolean(KEY_SAVE, false)
        set(value) = preferences.edit().putBoolean(KEY_SAVE, value).apply()

}