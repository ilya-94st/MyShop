package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.myshop.databinding.FragmentMainBinding
import com.example.myshop.presentation.base.BaseFragment
import com.example.myshop.presentation.ui.prefs

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMainBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView2.text = prefs.name
    }
}