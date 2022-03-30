package com.example.myshop.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.common.ProgressCircleGlide
import com.example.myshop.databinding.ItemsAddInOrderBinding
import com.example.myshop.domain.models.ProductsInCart

class ProductsAddInOrder: RecyclerView.Adapter<ProductsAddInOrder.ProductsViewHolder>() {

    inner class ProductsViewHolder(var binding: ItemsAddInOrderBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<ProductsInCart>() {
        override fun areItemsTheSame(oldItem: ProductsInCart, newItem: ProductsInCart): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductsInCart, newItem: ProductsInCart): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ProductsInCart>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemsAddInOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(products.image).placeholder(ProgressCircleGlide.progressBar(context)).
            into(holder.binding.ivProduct)
        }
        holder.binding.tvPrice.text = "${products.price}  ${products.currency}"
        holder.binding.tvTitle.text = products.title

    }

}
