package com.example.myshop.presentation.tools

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.myshop.R
import com.google.android.material.snackbar.Snackbar

fun SnackBar(view: View, text: String) {
    val snack = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    snack.show()
}

fun showErrorSnackBar(view: View, text: String, errorMessages: Boolean, context: Context){
    val snackBar =  Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    if(errorMessages) {
        snackBar.setBackgroundTint(ContextCompat.getColor(context, R.color.red))
    } else {
        snackBar.setBackgroundTint(ContextCompat.getColor(context, R.color.green))
    }
    snackBar.show()
}
