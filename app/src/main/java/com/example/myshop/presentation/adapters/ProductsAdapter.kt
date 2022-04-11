package com.example.myshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.common.ProgressCircleGlide
import com.example.myshop.databinding.ProductsItemsBinding
import com.example.myshop.domain.models.Products

class ProductsAdapter(private val listProducts: MutableList<Products>):  RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(var binding: ProductsItemsBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ProductsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products = listProducts[position]
        holder.itemView.apply {
            Glide.with(this).load(products.image).placeholder(ProgressCircleGlide.progressBar(context)).
            into(holder.binding.ivProduct)
        }

        holder.binding.tvPrice.text = "${products.price}  ${products.currency}"
        holder.binding.tvTitle.text = products.title
        holder.binding.ivDeleteProduct.setOnClickListener {
           onItemClickListener.let {
               it(products)
               listProducts.removeAt(position)
               notifyDataSetChanged()
           }
        }
    }

    private var onItemClickListener: (Products)->Unit = { products: Products -> Unit }

    fun setOnItemClickListener(listener: (Products) ->Unit) {
        onItemClickListener = listener
   }
}