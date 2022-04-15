package com.example.myshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemsAddressBinding
import com.example.myshop.domain.models.AddressUser


class AddressAdapter(private val listAddress: MutableList<AddressUser>): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(var binding: ItemsAddressBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemsAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAddress.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val itemsAddress = listAddress[position]
        holder.binding.tvMyAddress.text = itemsAddress.address
        holder.binding.tvName.text = itemsAddress.name
        holder.binding.tvNameAddress.text = itemsAddress.chooseAddress
        holder.binding.tvPhoneNumber.text = itemsAddress.phoneNumber.toString()
        holder.binding.tvZip.text = itemsAddress.zipCode

        holder.itemView.setOnClickListener {
            onItemClickListener.let {
                it(itemsAddress)
            }
        }
        holder.binding.ivDeleteProduct.setOnClickListener {
            onItemClickListenerDeleteItem.let {
                it(itemsAddress)
                listAddress.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }



    private var onItemClickListener: (AddressUser)->Unit = { addressUser: AddressUser -> Unit }

    fun setOnItemClickListener(listener: (AddressUser) ->Unit) {
        onItemClickListener = listener
    }

    private var onItemClickListenerDeleteItem: (AddressUser)->Unit = { addressUser: AddressUser -> Unit }

    fun setOnItemClickListenerDeleteItem(listener: (AddressUser) ->Unit) {
        onItemClickListenerDeleteItem = listener
    }
}