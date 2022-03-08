package com.example.myshop.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.databinding.AllProductsItemsBinding
import com.example.myshop.domain.models.Products

class AllProductsAdapter: RecyclerView.Adapter<AllProductsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(var binding: AllProductsItemsBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Products>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = AllProductsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(products.image).into(holder.binding.ivProduct)
        }
        holder.binding.tvDescriptions.text = products.description
        holder.binding.tvTitle.text = products.title
        holder.itemView.setOnClickListener {
            onItemClickListener.let {
               it(products)
            }
        }
    }

    private var onItemClickListener: (Products)->Unit = { products: Products -> Unit }

    fun setOnItemClickListener(listener: (Products) ->Unit) {
        onItemClickListener = listener
    }
}