package com.example.myshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.common.ProgressCircleGlide
import com.example.myshop.databinding.ItemsInCartBinding
import com.example.myshop.domain.models.ProductsInCart

class ProductsInCartAdapter(private val listProductsInCart: MutableList<ProductsInCart> , private val itemClickListenerCart: ItemClickListenerCart): RecyclerView.Adapter<ProductsInCartAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(var binding: ItemsInCartBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemsInCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listProductsInCart.size
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products = listProductsInCart[position]
              holder.itemView.apply {
            Glide.with(this).load(products.image).placeholder(ProgressCircleGlide.progressBar(context)).
            into(holder.binding.ivProduct)
        }
        holder.binding.tvPrice.text = "${products.price} ${products.currency}"
        holder.binding.tvTitle.text = products.title
        holder.binding.tvQuantity.text = "${products.quantity}"

        holder.binding.ibPlus.setOnClickListener {
            itemClickListenerCart.add(listProductsInCart[position], position)
        }
        holder.binding.ibMinus.setOnClickListener {
            itemClickListenerCart.minus(listProductsInCart[position], position)
        }
        holder.binding.ivDeleteProduct.setOnClickListener {
            itemClickListenerCart.deleteItem(products)
            listProductsInCart.removeAt(position)
            notifyDataSetChanged()
        }
    }

}
