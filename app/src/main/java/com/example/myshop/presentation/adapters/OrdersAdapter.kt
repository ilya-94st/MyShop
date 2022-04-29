package com.example.myshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.common.ProgressCircleGlide
import com.example.myshop.databinding.ItemsOrdersBinding
import com.example.myshop.domain.models.Products

import com.example.myshop.domain.models.ProductsInOrder

class OrdersAdapter :  RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    inner class OrdersViewHolder(var binding: ItemsOrdersBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<ProductsInOrder>() {
        override fun areItemsTheSame(oldItem: ProductsInOrder, newItem: ProductsInOrder): Boolean {
            return oldItem.idOrder== newItem.idOrder
        }

        override fun areContentsTheSame(oldItem:ProductsInOrder, newItem: ProductsInOrder): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ProductsInOrder>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = ItemsOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val products = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(products.image).placeholder(ProgressCircleGlide.progressBar(context)).
            into(holder.binding.ivProduct)
        }
        holder.binding.tvTitle.text = products.title + "${products.idOrder}"
        holder.itemView.setOnClickListener {
            onItemClickListener.let {
                it(products)
            }
        }
    }

    private var onItemClickListener: (ProductsInOrder)->Unit = { products: ProductsInOrder -> Unit }

    fun setOnItemClickListener(listener: (ProductsInOrder) ->Unit) {
        onItemClickListener = listener
    }
}
