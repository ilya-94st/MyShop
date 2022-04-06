package com.example.myshop.presentation.ui.fragments.add_address

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myshop.R
import com.example.myshop.common.EventClass
import com.example.myshop.databinding.FragmentAddAddressBinding
import com.example.myshop.domain.models.AddressUser
import com.example.myshop.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment : BaseFragment<FragmentAddAddressBinding>() {
    private val viewModel: AddAddressViewModel by viewModels()
    private var addressLocation = ""
    private var idAddress = 0L

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAddAddressBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeColorRadioGroup()
        binding.btSubmit.setOnClickListener {
            chooseAddressLocation()
            addUserAddress()
        }
        viewModel.result.observe(viewLifecycleOwner){ event ->
            when(event) {
                is EventClass.Success -> {
                    hideProgressDialog()
                    findNavController().popBackStack()
                }
                is EventClass.ErrorIn -> {
                    hideProgressDialog()
                    errorSnackBar(event.error, true)
                }
            }
        }
    }

    private fun addUserAddress() {
        showProgressDialog("Please wait...")
        viewModel.idAddress.observe(viewLifecycleOwner){
            idAddress = it
        }
        viewModel.user.observe(viewLifecycleOwner){
            val idUser = it.id
            val name = binding.etFullName.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val addressName = binding.etAddress.text.toString()
            val zipCode = binding.etZipCode.text.toString()
            val notes = binding.etNotes.text.toString()
            val addressUser = AddressUser(idUser, idAddress, name, addressName, phoneNumber.toLong(), zipCode, notes, addressLocation)
            viewModel.addAddressUser(addressUser, name, phoneNumber, addressName, zipCode, notes)
        }
    }

    private fun chooseAddressLocation() {
        addressLocation = when {
            binding.rbOffice.isChecked -> {
                "Office"
            }
            binding.rbHome.isChecked -> {
                "Home"
            }
            binding.rbOther.isChecked -> {
                binding.etDescription.text.toString()
            }
            else -> {
                "Office"
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeColorRadioGroup() {
        binding.rgAddress.setOnCheckedChangeListener { _, checkedID->
            when(checkedID) {
                R.id.rb_home -> {
                    binding.rbOffice.background = resources.getDrawable(R.drawable.empty_oval_button)
                    binding.rbHome.background = resources.getDrawable(R.drawable.oval_button)
                    binding.rbOther.background = resources.getDrawable(R.drawable.empty_oval_button)
                    binding.containerDescription.visibility = View.GONE
                    addressLocation = "Home"
                }
                R.id.rb_office -> {
                    binding.rbHome.background = resources.getDrawable(R.drawable.empty_oval_button)
                    binding.rbOffice.background = resources.getDrawable(R.drawable.oval_button)
                    binding.rbOther.background = resources.getDrawable(R.drawable.empty_oval_button)
                    binding.containerDescription.visibility = View.GONE
                    addressLocation = "Office"
                }
                R.id.rb_other -> {
                    binding.rbHome.background = resources.getDrawable(R.drawable.empty_oval_button)
                    binding.rbOther.background = resources.getDrawable(R.drawable.oval_button)
                    binding.rbOffice.background = resources.getDrawable(R.drawable.empty_oval_button)
                    binding.containerDescription.visibility = View.VISIBLE
                    addressLocation = binding.etDescription.text.toString()
                }
            }
        }
    }
}