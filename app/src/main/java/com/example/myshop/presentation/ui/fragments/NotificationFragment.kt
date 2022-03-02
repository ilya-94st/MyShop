package com.example.myshop.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.myshop.databinding.FragmentNotificationBinding
import com.example.myshop.presentation.base.BaseFragment


class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentNotificationBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}