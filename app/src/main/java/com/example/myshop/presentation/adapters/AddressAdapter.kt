package com.example.myshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemsAddressBinding
import com.example.myshop.domain.models.AddressUser


class AddressAdapter: RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(var binding: ItemsAddressBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<AddressUser>() {
        override fun areItemsTheSame(oldItem: AddressUser, newItem: AddressUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AddressUser, newItem: AddressUser): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<AddressUser>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemsAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val itemsAddress = differ.currentList[position]
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
    }

    private var onItemClickListener: (AddressUser)->Unit = { addressUser: AddressUser -> Unit }

    fun setOnItemClickListener(listener: (AddressUser) ->Unit) {
        onItemClickListener = listener
    }
}