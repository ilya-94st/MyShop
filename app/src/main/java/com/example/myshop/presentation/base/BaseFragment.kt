package com.example.myshop.presentation.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.presentation.tools.SnackBar
import com.example.myshop.presentation.tools.showErrorSnackBar
import com.example.myshop.presentation.tools.toast

abstract class BaseFragment<out T : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    private lateinit var mProgressDialog: Dialog


    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = _binding as T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater(inflater)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract val bindingInflater: (LayoutInflater) -> ViewBinding


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun snackBar(text: String) {
        view?.let {
            SnackBar(requireView() ,text)
        }
    }


    fun showProgressDialog(message: String) {
        mProgressDialog = Dialog(requireContext())

        mProgressDialog.setContentView(R.layout.dialog_progres)

        mProgressDialog.findViewById<TextView>(R.id.tvDialog).text = message

        mProgressDialog.setCancelable(false)

        mProgressDialog.setCanceledOnTouchOutside(false)

        mProgressDialog.show()
    }



    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun errorSnackBar(text: String, errorMessages: Boolean){
        view?.let {
            context?.let { it1 -> showErrorSnackBar(requireView(),text,errorMessages, it1) }
        }
    }

    fun toast(message: String) {
        requireContext().toast(message)
    }
}